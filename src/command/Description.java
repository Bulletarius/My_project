package command;

import game.GameData;
import game.State;
import thoseBoringClassesThatExistJustToStoreThings.Skill;

/**
 * A command to print extra information about a skill or enemy.
 * @author Patrik Novotný
 */
public class Description implements Command{
    private final GameData data;

    public Description(GameData data){
        this.data = data;
    }

    /**
     * A command to print extra information about a skill or enemy.
     * @param argument the second part of the command used as an argument
     * @param state    current state of the game
     * @return the same state as provided as it can not be changed here
     */
    @Override
    public State execute(String argument, State state) {
        Skill skill = data.getSkill(argument);
        if (skill == null) {
            try {
                System.out.println(data.getEnemy(argument).getName() + ", " + data.getEnemy(argument).getDescription());
            }catch (NullPointerException e){
                System.out.println("That enemy / skill does not exist");
            }

        }else System.out.println(skill.getName() + ", " + skill.getDescription());

        return state;
    }
}
