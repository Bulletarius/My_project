package command;

import game.State;

public interface Command {
    /**
     * @param argument the second part of the command used as an argument
     * @param state current state of the game
     */
     State execute(String argument, State state);
}
