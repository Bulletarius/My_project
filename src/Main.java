import game.GameData;

public class Main {
    public static void main(String[] args) {
        GameData data = GameData.loadGameDataFromResources("resources/gamedata.json");
        data.toMap();

    }
}