package command;

import game.CurrentData;
import game.State;

public class Use implements Command{
    private CurrentData cData;

    public Use(CurrentData data){
        cData = data;
    }

    /**
     * @param argument the second part of the command used as an argument
     * @param state    current state of the game
     */
    @Override
    public State execute(String argument, State state) {
        if (state == State.COMBAT){
            if(argument != null){
                cData.chooseSkill(argument);
            }else System.out.println("Command requires an argument");
        }else System.out.println("Only usable in combat");
        return state;
    }
}
