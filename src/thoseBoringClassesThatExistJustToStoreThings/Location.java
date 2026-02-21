package thoseBoringClassesThatExistJustToStoreThings;

import java.util.ArrayList;

/**
 * A class to store and represent a location.
 * @author Patrik Novotný
 */
public class Location {
    private int id;
    private String name;
    private String dialogueFileBefore;
    private String dialogueFileAfter;
    private ArrayList<String> enemies;
    private ArrayList<String> loot;

    public Location(){}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDialogueFileBefore() {
        return dialogueFileBefore;
    }
    public void setDialogueFileBefore(String dialogueFileBefore) {
        this.dialogueFileBefore = dialogueFileBefore;
    }

    public String getDialogueFileAfter() {
        return dialogueFileAfter;
    }
    public void setDialogueFileAfter(String dialogueFileAfter) {
        this.dialogueFileAfter = dialogueFileAfter;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public ArrayList<String> getEnemies(){return enemies;}

    public ArrayList<String> getLoot(){return loot;}
}
