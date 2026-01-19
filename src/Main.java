public class Main {
    public static void main(String[] args) {
        GameData data = GameData.loadGameDataFromResources("/gamedata.json");
        System.out.println(data.getLocations());
    }
}