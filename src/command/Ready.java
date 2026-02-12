package command;

import game.CurrentData;
import game.State;

public class Ready implements Command{
    private CurrentData cData;

    public Ready(CurrentData cData){
        this.cData = cData;
    }

    @Override
    public State execute(String argument, State state) {
        if (state == State.COMBAT){
            cData.evaluate();
        }else System.out.println("not in combat");
        return state;
    }
}
