package game;

import command.Command;
import command.GoTo;
import command.Help;
import command.Stop;

import java.util.HashMap;
import java.util.Scanner;
//TODO write JavaDoc
public class Game {
    GameData data;
    HashMap<String, Command> commands;
    State state;
    CurrentData cData;
    //TODO write JavaDoc
    public Game(){
        data = GameData.loadGameDataFromResources("resources/gamedata.json");
        data.toMap();
        cData = new CurrentData();
        commands = new HashMap<>();
        commands.put("go", new GoTo(cData, data));
        commands.put("stop", new Stop());
        commands.put("help", new Help(commands));
        state = State.IDLE;
    }
    //TODO write JavaDoc
    public void run(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print('>');
            String command = scanner.nextLine();
            String[] split =command.toLowerCase().trim().split(" ", 2);
            Command executable =commands.get(split[0]);
            if(split.length == 1){
                executable.execute(null, state);
            }else executable.execute(split[1], state);
        }
    }
}