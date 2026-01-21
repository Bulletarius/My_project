import game.GameData;

public class Main {
    public static void main(String[] args) {
        GameData data = GameData.loadGameDataFromResources("/gamedata.json");
        data.toMap();

    }
}