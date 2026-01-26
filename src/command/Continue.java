package command;

import game.CurrentData;
import game.State;

public class Continue implements Command{
    private CurrentData cdata;

    public Continue(CurrentData cdata){
        this.cdata = cdata;
    }

    @Override
    public State execute(String argument, State state) {
        if (state == State.DIALOGUE) {
            if (cdata.stepDialogue()) {
                return State.DIALOGUE;
            } else {
                return State.COMBAT;
            }
        }else System.out.println("No one is talking");return state;
    }
}
