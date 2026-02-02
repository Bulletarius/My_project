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


    //TODO write JavaDoc
    public CurrentData(GameData data){
        inventory = new Inventory();
        currentLocation = 0;
        unlockedLocation = 3;
        enemies = new LinkedList<>();
        enemySkills = new ArrayList<>();
        playerSkills = new LinkedList<>();
        this.data = data;
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
        for (String enemy : data.getLocation(currentLocation).getEnemies()){
            enemies.add(data.getEnemy(enemy).clone());
        }
        playerHealth = 100;
        inventory.makeCurrentDeck();
        startTurn();
    }

    public void startTurn(){
        Random random = new Random();
        for (Enemy enemy : enemies){
            ArrayList<String> skills = enemy.getSkills();
            for (int i = 0; i < enemy.getSkillSlots(); i++) {
                enemySkills.add(data.getSkill(skills.get(random.nextInt(skills.size()))).clone(enemy));
            }
        }
        iterator = enemySkills.listIterator();
        inventory.makeHand(enemySkills.size());
        stamina += random.nextInt(4) * Math.round((enemySkills.size() + 1) * 0.5f);
        currentskill = enemySkills.getFirst();
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
            printCurrentStatus();
            stamina += skill.getCost();
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
        }
        iterator = null;
        iterator = enemySkills.listIterator();
        //ListIterator<Skill> pIterator = playerSkills.listIterator();
        for (Skill skill : playerSkills){
            int playerPower = skill.getBasePower();
            for (int i = 0; i < skill.getCoinCount(); i++) {

            }
        }
    }
}
