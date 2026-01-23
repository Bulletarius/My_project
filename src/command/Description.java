package command;

import game.GameData;
import game.State;
import thoseBoringClassesThatExistJustToStoreThings.Enemy;
import thoseBoringClassesThatExistJustToStoreThings.Skill;

public class Description implements Command{
    private GameData data;

    public Description(GameData data){
        this.data = data;
    }

    /**
     * @param argument the second part of the command used as an argument
     * @param state    current state of the game
     */
    @Override
    public void execute(String argument, State state) {
        Skill skill = data.getSkill(argument);
        if (skill == null) {
            System.out.println(data.getEnemy(argument));

        }else System.out.println(skill);

    }
}
