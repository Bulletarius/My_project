package game;

import thoseBoringClassesThatExistJustToStoreThings.Enemy;
import thoseBoringClassesThatExistJustToStoreThings.Location;
import thoseBoringClassesThatExistJustToStoreThings.Skill;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

//TODO write JavaDoc
public class CurrentData {
    private Inventory inventory;
    private int playerHealth;
    private int currentLocation;
    private int unlockedLocation;
    private GameData data;
    private BufferedReader reader;
    private LinkedList<Enemy> enemies;
    private ArrayList<Skill> enemySkills;
    private LinkedList<Skill> playerSkills;
    private ListIterator<Skill> iterator;
    private Skill currentskill;
    private int stamina;
    private int sanity;
    private Random random;


    //TODO write JavaDoc
    public CurrentData(GameData data){
        inventory = new Inventory(data);
        currentLocation = 0;
        unlockedLocation = 3;
        enemies = new LinkedList<>();
        enemySkills = new ArrayList<>();
        playerSkills = new LinkedList<>();
        this.data = data;
        random = new Random();
    }

    public Inventory getInventory() {return inventory;}

    public int getPlayerHealth() {
        return playerHealth;
    }
    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }

    public int getCurrentLocation() {
        return currentLocation;
    }
    public void setCurrentLocation(int currentLocation) {
        this.currentLocation = currentLocation;
        Location location = data.getLocation(currentLocation);
        try {
            reader = new BufferedReader(new FileReader(location.getDialogueFileBefore()));
            stepDialogue();
        }catch (Exception e){
            System.out.println("Unable to load dialogue file, placeholder dialogue");
        }
    }

    public int getUnlockedLocation() {
        return unlockedLocation;
    }
    public void setUnlockedLocation(int unlockedLocation) {
        this.unlockedLocation = unlockedLocation;
    }

    public boolean stepDialogue(){
        try {
            String line = reader.readLine();
            if (line != null) {
                System.out.println(line);
                return true;
            } else{
                reader.close();
                startCombat();
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void startCombat(){
        stamina = 5;
        for (String enemy : data.getLocation(currentLocation).getEnemies()){
            enemies.add(data.getEnemy(enemy).clone());
        }
        playerHealth = 100;
        inventory.makeCurrentDeck();
        startTurn();
    }

    public void startTurn(){
        for (Enemy enemy : enemies){
            ArrayList<String> skills = enemy.getSkills();
            for (int i = 0; i < enemy.getSkillSlots(); i++) {
                enemySkills.add(data.getSkill(skills.get(random.nextInt(skills.size()))).clone(enemy));
            }
        }
        iterator = enemySkills.listIterator();
        inventory.makeHand(enemySkills.size());
        stamina += random.nextInt(4) * Math.round((enemySkills.size() + 1) * 0.5f);
        currentskill = enemySkills.get(0);
        printStatus();
        try{Thread.sleep(2000);} catch (InterruptedException ignored) {}
        printCurrentStatus();
    }

    public void printStatus(){
        String string = "";
        int j = 0;
        for (Enemy enemy : enemies){
            string = string.concat(enemy.toString());
            for (int i = 0; i < enemy.getSkillSlots(); i++) {
                string = string.concat(enemySkills.get(j++).toString());
            }
            string = string.concat(System.lineSeparator());
        }
        System.out.println(string);
    }

    public void chooseSkill(String string){
        if(enemySkills.size() <= playerSkills.size()){
            System.out.println("You already have all skills equipped");
            return;
        }
        Skill skill = data.getSkill(string);
        if (skill == null) {
            System.out.println("That skill does not exist");
            return;
        }
        if (skill.getCost() > stamina){
            System.out.println("You do not have enough cost");
            return;
        }
        if (inventory.removeHand(skill)){
            stamina -= skill.getCost();
            playerSkills.add(skill);
            currentskill = iterator.next();
            printCurrentStatus();
        }else System.out.println("That skill is not in your hand");
    }

    public void undo(){
        Skill skill = playerSkills.pollLast();
        if (skill != null) {
            inventory.addHand(skill);
            currentskill = iterator.previous();
            System.out.println("success");
            stamina += skill.getCost();
            printCurrentStatus();
        }else System.out.println("There is nothing to undo");
    }

    public void printCurrentStatus(){
        System.out.println(currentskill.getOwner());
        System.out.println(currentskill + System.lineSeparator());
        System.out.println("cost:" + stamina);
        System.out.println(inventory.printHand());
    }

    public void evaluate(){
        if(enemySkills.size() != playerSkills.size()){
            System.out.println("You have not equipped all skills");
            return;
        }
        iterator = null;
        iterator = enemySkills.listIterator();
        //ListIterator<Skill> pIterator = playerSkills.listIterator();
        for (Skill playerSkill : playerSkills){
            Skill enemySkill = iterator.next();
            while (!(playerSkill.getCoinCount() ==0 || enemySkill.getCoinCount() == 0 )){
                int playerPower = playerSkill.getBasePower();
                for (int i = 0; i < playerSkill.getCoinCount(); i++) {
                    if (roll(sanity)) {
                        playerPower += playerSkill.getCoinPower();
                        System.out.print(" ◙" + playerPower);
                    } else System.out.print(" x" + playerPower);
                }
                System.out.print("   :   ");
                int enemyPower = enemySkill.getBasePower();
                for (int i = 0; i < enemySkill.getCoinCount(); i++) {
                    if (roll(enemySkill.getOwner().getSanity())) {
                        enemyPower += enemySkill.getCoinPower();
                        System.out.print(" ◙" + enemyPower);
                    } else System.out.print(" x" + enemyPower);
                }
                System.out.println();
                if (playerPower > enemyPower) {
                    enemySkill.looseCoin();
                    System.out.println("enemy lost a coin");
                } else if (playerPower < enemyPower) {
                    playerSkill.looseCoin();
                    System.out.println("you lost a coin");
                }
            }
            int damage;
            if (enemySkill.getCoinCount() == 0) {
                damage = playerSkill.getBasePower();
                for (int i = 0; i < playerSkill.getCoinCount(); i++) {
                    if (roll(sanity)){
                        damage += playerSkill.getCoinPower();
                        System.out.print("◙");
                    }else System.out.print("x");
                    enemySkill.getOwner().changeHealth(damage);
                    System.out.println(damage + "damage. remaining Health: "+ enemySkill.getOwner().getHealth());
                }
            }else {
                damage = enemySkill.getBasePower();
                for (int i = 0; i < enemySkill.getCoinCount(); i++) {
                    if (roll(enemySkill.getOwner().getSanity())) {
                        damage += enemySkill.getCoinPower();
                        System.out.print("◙");
                    }else System.out.print("x");
                    playerHealth -= damage;
                    System.out.println(damage + "damage. remaining Health: "+ playerHealth);
                }
            }
            System.out.println();
        }
        enemySkills.clear();
        playerSkills.clear();
        startTurn();
    }

    private boolean roll(int sanity){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return random.nextInt(-50, 51) >= sanity;
    }
}
