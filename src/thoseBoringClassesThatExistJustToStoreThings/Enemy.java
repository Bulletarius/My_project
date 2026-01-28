package thoseBoringClassesThatExistJustToStoreThings;

import java.util.ArrayList;

public class Enemy {
    private String id;
    private String name;
    private int health;
    private ArrayList<String> skills;
    private String description;
    private int skillSlots;
    private int sanity;

    public Enemy(String id, String name, int health, ArrayList<String> skills, String description, int skillSlots, int sanity) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.skills = skills;
        this.description = description;
        this.skillSlots = skillSlots;
        this.sanity = sanity;
    }

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

    public int getSkillSlots(){return skillSlots;}

    public int getSanity(){return sanity;}
    public void changeSanity(int change){sanity =+ change;}

    @Override
    public String toString() {
        return name + ": Health:" + health + ", Sanity:" + sanity + "skills: [";
    }

    @Override
    public Enemy clone() {
        return new Enemy(id,name,health, skills, description, skillSlots, sanity);
    }
}
