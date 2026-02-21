package game;

import command.*;

import java.util.HashMap;
import java.util.Scanner;

/**
 * The console class.
 * @author Patrik Novotný
 */
public class Game {
    private GameData data;
    private HashMap<String, Command> commands;
    private State state;
    private CurrentData cData;
    private boolean exit = false;


    public Game(){
        data = GameData.loadGameDataFromResources("/gamedata.json");
        data.toMap();
        cData = new CurrentData(data);
        commands = new HashMap<>();
        commands.put("go", new GoTo(cData, data));
        commands.put("stop", new Stop(this));
        commands.put("help", new Help(commands));
        commands.put("description", new Description(data));
        commands.put("c", new Continue(cData));
        commands.put("undo", new Undo(cData));
        commands.put("use", new Use(cData));
        commands.put("equip", new Equip(cData, data));
        commands.put("unequip", new Unequip(cData, data));
        commands.put("inventory", new PrintInventory(cData));
        commands.put("ready", new Ready(cData));
        state = State.DIALOGUE;
        cData.setCurrentLocation(0);
    }

    /**
     *The main loop that reads the input and executes commands.
     */
    public void run(){
        Scanner scanner = new Scanner(System.in);
        while(!exit){
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

    /**
     * sets an internal flag to true to stop the program next time
     */
    public void setExit(){
        exit = true;
    }
}