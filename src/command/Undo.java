package command;

import game.CurrentData;
import game.State;

public class Undo implements Command{
    private CurrentData cdata;

    public Undo(CurrentData data){
        cdata = data;
    }

    /**
     * @param argument the second part of the command used as an argument
     * @param state    current state of the game
     */
    @Override
    public State execute(String argument, State state) {
        if (state == State.COMBAT){
            cdata.undo();
        }
        return state;
    }
}
