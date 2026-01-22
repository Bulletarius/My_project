package game;

import com.google.gson.Gson;
import com.sun.tools.javac.Main;
import thoseBoringClassesThatAreJustToStoreThings.Enemy;
import thoseBoringClassesThatAreJustToStoreThings.Location;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class GameData {
    private ArrayList<Location> locations;
    private ArrayList<Enemy> enemies;
    private HashMap<String, Enemy> enemiesMap;



    /**
     * Loads game data from a JSON file.
     * @param resourcePath path to the resource file
     * @return a game.GameData object filled with the loaded data
     */
    public static GameData loadGameDataFromResources(String resourcePath) {
        //Vytvoření objektu pro práci s JSON souborem
        Gson gson = new Gson();

        //Načtení souboru gamedata.json, musí být ve složce res/resources, ta musí být označena jako resource složka projektu
        try (Reader reader = new FileReader(resourcePath)) {

            //Přečte celý JSON a vytvoří instanci game.GameData, naplní vlastnosti podle názvů klíčů v JSONU, vrátí se hotová třída game.GameData
            return gson.fromJson(
                    reader,
                    GameData.class
            );

        } catch (Exception e) {
            throw new RuntimeException("Chyba při načítání JSON: " + e.getMessage());
        }

    }

    /**
     * Converts internal arraylists to Hash maps
     */
    public void toMap(){
        enemiesMap = new HashMap<>();
        for (Enemy enemy : enemies) {
            enemiesMap.put(enemy.getId(), enemy);
        }
    }

}
