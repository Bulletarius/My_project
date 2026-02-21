package command;

import game.State;

/**
 * Interface of the commands.
 * @author Patrik Novotný
 */
public interface Command {
    /**
     * the code that runs as part of the command
     * @param argument the second part of the command used as an argument
     * @param state current state of the game
     * @return the next state that should be set
     */
     State execute(String argument, State state);
}
