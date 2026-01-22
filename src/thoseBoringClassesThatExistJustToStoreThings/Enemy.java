package thoseBoringClassesThatExistJustToStoreThings;

import java.util.ArrayList;

public class Enemy {
    private String id;
    private String name;
    private int health;
    private ArrayList<String> skills;
    private String description;

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getHealth() {return health;}
    public void setHealth(int health) {this.health = health;}

    public ArrayList<String> getSkills() {return skills;}
    public void setSkills(ArrayList<String> skills) {this.skills = skills;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    @Override
    public String toString() {
        return name + ":" + description + ", skills=" + skills;
    }
}
