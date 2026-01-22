package command;

import game.State;

public class Stop implements Command{
    /**
     * Stops the program. The arguments do not matter
     */
    @Override
    public void execute(String argument, State state) {
        System.exit(0);
    }
}
