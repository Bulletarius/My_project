package game;

import thoseBoringClassesThatExistJustToStoreThings.Location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//TODO write JavaDoc
public class CurrentData {
    private Inventory inventory;
    private int playerHealth;
    private int currentLocation;
    private int unlockedLocation;
    private GameData data;
    private BufferedReader reader;
    //TODO write JavaDoc
    public CurrentData(GameData data){
        inventory = new Inventory();
        playerHealth = 100;
        currentLocation = 0;
        unlockedLocation = 3;
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
                return false;
            }

        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }

    public void startCombat(){

    }
}
