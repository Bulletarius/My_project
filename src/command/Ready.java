package command;

import game.CurrentData;
import game.State;

/**
 * A command to evaluate and print the skills chosen by the player and enemies.
 * @author Patrik Novotný
 */
public class Ready implements Command{
    private CurrentData cData;

    public Ready(CurrentData cData){
        this.cData = cData;
    }

    /**
     * A command to evaluate and print the skills chosen by the player and enemies.
     * @param argument the second part of the command used as an argument
     * @param state current state of the game
     * @return the next state that should be set
     */
    @Override
    public State execute(String argument, State state) {
        if (state == State.COMBAT){
            return cData.evaluate();
        }else System.out.println("not in combat");
        return state;
    }
}
