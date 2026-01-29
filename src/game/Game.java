package game;

import command.*;

import java.util.HashMap;
import java.util.Scanner;
//TODO write JavaDoc
public class Game {
    private GameData data;
    private HashMap<String, Command> commands;
    private State state;
    private CurrentData cData;
    //TODO write JavaDoc
    public Game(){
        data = GameData.loadGameDataFromResources("resources/gamedata.json");
        data.toMap();
        cData = new CurrentData(data);
        commands = new HashMap<>();
        commands.put("go", new GoTo(cData, data));
        commands.put("stop", new Stop());
        commands.put("help", new Help(commands));
        commands.put("description", new Description(data));
        commands.put("c", new Continue(cData));
        commands.put("undo", new Undo(cData));
        commands.put("use", new Use(cData));
        state = State.DIALOGUE;
        cData.setCurrentLocation(0);
    }
    //TODO write JavaDoc
    public void run(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print('>');
            String command = scanner.nextLine();
            String[] split =command.toLowerCase().trim().split(" ", 2);
            Command executable =commands.get(split[0]);
            if (executable == null) {
               System.out.println("Invalid command, try using 'help'");
            }else if(split.length == 1){
                    state = executable.execute(null, state);
            }else state = executable.execute(split[1], state);
        }
    }
}