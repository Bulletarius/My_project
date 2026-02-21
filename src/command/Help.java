package command;

import game.State;
import java.util.HashMap;

/**
 * A command to print all possible commands.
 * @author Patrik Novotný
 */
public class Help implements Command{
    private HashMap<String,Command> commands;

    public Help(HashMap<String, Command> commands){
        this.commands = commands;
    }

    /**
     * prints out the keys of the hash map as the available commands
     * the parameters do not matter
     */
    @Override
    public State execute(String argument, State state) {
        System.out.println("Commands: " + commands.keySet());
        return state;
    }
}
