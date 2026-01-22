package game;

import com.google.gson.Gson;
import thoseBoringClassesThatExistJustToStoreThings.Enemy;
import thoseBoringClassesThatExistJustToStoreThings.Location;
import thoseBoringClassesThatExistJustToStoreThings.Skill;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * a class to store manage and access game data
 */
public class GameData {
    private ArrayList<Location> locations;
    private ArrayList<Enemy> enemies;
    private ArrayList<Skill> skills;
    private HashMap<String, Enemy> enemiesMap;
    private HashMap<String, Skill> skillsMap;




    /**
     * Loads game data from a JSON file.
     * @param resourcePath path to the resource file
     * @return a game.GameData object filled with the loaded data
     */
    public static GameData loadGameDataFromResources(String resourcePath) {
        Gson gson = new Gson();

        try (Reader reader = new FileReader(resourcePath)) {

            return gson.fromJson(reader, GameData.class);

        } catch (Exception e) {
            throw new RuntimeException("Error loading JSON: " + e.getMessage());
        }

    }

    /**
     * Converts internal array Lists to Hash maps,
     * needs to be called to properly initialize
     */
    public void toMap(){
        enemiesMap = new HashMap<>();
        for (Enemy enemy : enemies) {
            enemiesMap.put(enemy.getId(), enemy);
        }
        skillsMap = new HashMap<>();
        for (Skill skill :skills){
            skillsMap.put(skill.getId(),skill);
        }
    }

    /**
     * returns the location based on the id,
     * is overloaded with a name based function
     * @param id index / location
     * @return the location with the index
     */
    public Location getLocation(int id){
        return locations.get(id);
    }

    /**
     * returns the location based on a name,
     * is overloaded with an id based function
     * @param name the name that gets searched for
     * @return the location with the name
     */
    public Location getLocation(String name){
        for(Location location : locations){
            if(location.getName().toLowerCase().equals(name)){
                return location;
            }
        }
        return null;
    }

    /**
     * returns an enemy object based on the id
     * @param id the id to get
     * @return the enemy with the matching id
     */
    public Enemy getEnemy(String id){
        return enemiesMap.get(id);
    }

    /**
     * returns an enemy object based on name
     * @param name the name to get
     * @return the enemy with the matching name
     */
    public Enemy getEnemyName(String name){
        for(Enemy enemy : enemies){
            if(enemy.getName().toLowerCase().equals(name)){
                return enemy;
            }
        }
        return null;
    }
    /**
     * returns a skill object based on the id
     * @param id the id to get
     * @return the skill with the matching id
     */
    public Skill getSkill(String id){
        return skillsMap.get(id);
    }

    /**
     * returns a skill object based on name
     * @param name the name to get
     * @return the skill with the matching name
     */
    public Skill getSkillName(String name){
        for(Skill skill : skills){
            if(skill.getName().toLowerCase().equals(name)){
                return skill;
            }
        }
        return null;
    }
}
