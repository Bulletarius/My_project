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
 * @author Michaela Meintnerová, Patrik Novotný, Matěj Chaloupka
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
     * @return the location with the name, null if it can not be found
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
     * returns an enemy object based on the id or name as it searches both
     * but prioritizes id
     * @param id the id or name to get corresponding enemy
     * @return the enemy with the matching id or name, null if it can not be found
     */
    public Enemy getEnemy(String id){
        if (enemiesMap.containsKey(id)){
            return enemiesMap.get(id);
        }else{
            for(Enemy enemy : enemies){
                if(enemy.getName().toLowerCase().equals(id)){
                    return enemy;
                }
            }
            return null;
        }

    }

    /**
     * returns a skill object based on the id or name as it searches both
     * but prioritizes id
     * @param id the id or name to get corresponding skill
     * @return the skill with the matching id or name, null if it can not be found
     */
    public Skill getSkill(String id){
        if (skillsMap.containsKey(id)){
            return skillsMap.get(id);
        }else {
            for(Skill skill : skills){
                if(skill.getName().toLowerCase().equals(id)){
                    return skill;
                }
            }
            return null;
        }
    }

}
