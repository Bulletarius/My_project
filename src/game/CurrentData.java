package game;

public class CurrentData {
    private int playerHealth;
    private int currentLocation;
    private int unlockedLocation;

    public CurrentData(){
        playerHealth = 100;
        currentLocation = 0;
        unlockedLocation = 3;
    }

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
    }

    public int getUnlockedLocation() {
        return unlockedLocation;
    }
    public void setUnlockedLocation(int unlockedLocation) {
        this.unlockedLocation = unlockedLocation;
    }
}
