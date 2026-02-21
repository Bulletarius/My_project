package command;

import game.CurrentData;
import game.State;

/**
 * A command to undo a choice of a skill
 * @author Patrik Novotný
 */
public class Undo implements Command{
    private CurrentData cdata;

    public Undo(CurrentData data){
        cdata = data;
    }

    /**
     * A command to undo a choice of a skill
     * @param argument the second part of the command used as an argument
     * @param state    current state of the game
     * @return the next state that should be set
     */
    @Override
    public State execute(String argument, State state) {
        if (state == State.COMBAT){
            cdata.undo();
        }
        return state;
    }
}
