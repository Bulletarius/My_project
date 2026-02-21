package game;

import thoseBoringClassesThatExistJustToStoreThings.Enemy;
import thoseBoringClassesThatExistJustToStoreThings.Location;
import thoseBoringClassesThatExistJustToStoreThings.Skill;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/**
 * A class that handles basically everything
 * @author Patrik Novotný
 */
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
    private State nextState;


    public CurrentData(GameData data){
        inventory = new Inventory(data);
        unlockedLocation = 0;
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

    /**
     * sets the current location to the one
     * @param currentLocation the location to go to
     */
    public void setCurrentLocation(int currentLocation) {
        this.currentLocation = currentLocation;
        Location location = data.getLocation(currentLocation);
        nextState = State.COMBAT;
        reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(location.getDialogueFileBefore())));
        stepDialogue();
    }

    public int getUnlockedLocation() {
        return unlockedLocation;
    }
    public void setUnlockedLocation(int unlockedLocation) {
        this.unlockedLocation = unlockedLocation;
    }

    /**
     * Prints a next line from the dialogue file.
     * @return state that should be set
     */
    public State stepDialogue(){
        try {
            String line = reader.readLine();
            if (line != null) {
                System.out.println(line);
                return State.DIALOGUE;
            } else{
                reader.close();
                if (nextState == State.COMBAT) {
                    startCombat();
                }
                return nextState;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return nextState;
        }
    }

    private void startCombat(){
        stamina = 5;
        for (String enemy : data.getLocation(currentLocation).getEnemies()){
            enemies.add(data.getEnemy(enemy).clone());
        }
        playerHealth = 100;
        inventory.makeCurrentDeck();
        startTurn();
    }

    private void startTurn(){
        for (Enemy enemy : enemies){
            ArrayList<String> skills = enemy.getSkills();
            for (int i = 0; i < enemy.getSkillSlots(); i++) {
                enemySkills.add(data.getSkill(skills.get(random.nextInt(skills.size()))).clone(enemy));
            }
        }
        iterator = enemySkills.listIterator();
        inventory.makeHand(enemySkills.size()*2);
        stamina += random.nextInt(4) * Math.round((enemySkills.size() + 1) * 0.5f);
        currentskill = iterator.next();
        printStatus();
        try{Thread.sleep(2000);} catch (InterruptedException ignored) {}
        printCurrentStatus();
    }

    private void printStatus(){
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

    /**
     * Method to add a skill to the internal list of player skills.
     * @param string Name or id of the skill that was chosen.
     */
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
            playerSkills.add(skill.clone(null));
            if (iterator.hasNext()){
                currentskill = iterator.next();
                printCurrentStatus();
            }else System.out.println("You have now used all skills");
        }else System.out.println("That skill is not in your hand");
    }

    /**
     * removes the last skill from the internal list.
     */
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

    private void printCurrentStatus(){
        System.out.println(currentskill.getOwner());
        System.out.println(currentskill + System.lineSeparator());
        System.out.println("cost:" + stamina + " sanity:" + sanity);
        System.out.println(inventory.printHand());
    }

    /**
     * A method to evaluate and print the skills chosen by the player and enemies.
     * @return state that should be set
     */
    public State evaluate(){
        if(enemySkills.size() != playerSkills.size()){
            System.out.println("You have not equipped all skills");
            return State.COMBAT;
        }
        iterator = null;
        iterator = enemySkills.listIterator();
        for (Skill playerSkill : playerSkills){
            Skill enemySkill = iterator.next();
            while (!(playerSkill.getCoinCount() ==0 || enemySkill.getCoinCount() == 0 )){
                int playerPower = playerSkill.getBasePower();
                for (int i = 0; i < playerSkill.getCoinCount(); i++) {
                    if (roll(sanity)) {
                        playerPower += playerSkill.getCoinPower();
                        System.out.print(" ◙" + playerPower);
                    } else System.out.print(" ×" + playerPower);
                }
                System.out.print("   :   ");
                int enemyPower = enemySkill.getBasePower();
                for (int i = 0; i < enemySkill.getCoinCount(); i++) {
                    if (roll(enemySkill.getOwner().getSanity())) {
                        enemyPower += enemySkill.getCoinPower();
                        System.out.print(" ◙" + enemyPower);
                    } else System.out.print(" ×" + enemyPower);
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
                System.out.println("You won the clash");
                sanity += 10;
                damage = playerSkill.getBasePower();
                for (int i = 0; i < playerSkill.getCoinCount(); i++) {
                    if (roll(sanity)){
                        damage += playerSkill.getCoinPower();
                        System.out.print("◙");
                    }else System.out.print("×");
                    enemySkill.getOwner().changeHealth(damage);
                    System.out.println(damage + "damage. remaining Health: "+ enemySkill.getOwner().getHealth());
                }
            }else {
                System.out.println("Enemy won the clash");
                enemySkill.getOwner().changeSanity(10);
                damage = enemySkill.getBasePower();
                for (int i = 0; i < enemySkill.getCoinCount(); i++) {
                    if (roll(enemySkill.getOwner().getSanity())) {
                        damage += enemySkill.getCoinPower();
                        System.out.print("◙");
                    }else System.out.print("×");
                    playerHealth -= damage;
                    System.out.println(damage + "damage. remaining Health: "+ playerHealth);
                }
            }
            System.out.println();
        }
        enemies.removeIf(enemy -> enemy.getHealth() <= 0);
        if (enemies.isEmpty()){
            reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(data.getLocation(currentLocation).getDialogueFileAfter())));
            stepDialogue();
            nextState = State.IDLE;
            if (currentLocation == unlockedLocation){
                unlockedLocation++;
            }
            return State.DIALOGUE;
        }else if (playerHealth <= 0) {
            System.out.println("You lost");
            return State.IDLE;
        }else {
            enemySkills.clear();
            playerSkills.clear();
            inventory.returnHand();
            startTurn();
            return State.COMBAT;
        }
    }

    private boolean roll(int sanity){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return !(random.nextInt(-50, 51) < sanity);
    }
}
