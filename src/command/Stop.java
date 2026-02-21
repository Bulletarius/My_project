package command;

import game.Game;
import game.State;

/**
 * A command to stop the program.
 * @author Patrik Novotný
 */
public class Stop implements Command{
    private final Game game;

    public Stop(Game game){
        this.game =game;
    }
    /**
     * Stops the program. The arguments do not matter
     */
    @Override
    public State execute(String argument, State state) {
        game.setExit();
        return state;
    }
}
