package command;

import game.CurrentData;
import game.State;

/**
 * Command to print another line of dialogue.
 * @author Patrik Novotný
 */
public class Continue implements Command{
    private CurrentData cdata;

    public Continue(CurrentData cdata){
        this.cdata = cdata;
    }

    /**
     * Command to print another line of dialogue.
     * @param argument the second part of the command used as an argument
     * @param state current state of the game
     * @return the next state that should be set
     */
    @Override
    public State execute(String argument, State state) {
        if (state == State.DIALOGUE) {
            return cdata.stepDialogue();
        }else System.out.println("No one is talking");return state;
    }
}
