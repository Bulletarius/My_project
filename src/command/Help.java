package command;

import game.State;
import java.util.HashMap;
//TODO write JavaDoc
public class Help implements Command{
    private HashMap<String,Command> commands;
    //TODO write JavaDoc
    public Help(HashMap<String, Command> commands){
        this.commands = commands;
    }

    /**
     * prints out the keys of the hash map as the available commands
     * the parameters do not matter
     */
    @Override
    public void execute(String argument, State state) {
        System.out.println("Commands: " + commands.keySet());
    }
}
