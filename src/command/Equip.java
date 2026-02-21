package command;

import game.CurrentData;
import game.GameData;
import game.Inventory;
import game.State;

/**
 * A command to add a skill to the deck
 * @author Patrik Novotný
 */
public class Equip implements Command{
    private Inventory inventory;
    private GameData data;

    public Equip(CurrentData cData, GameData data){
        inventory = cData.getInventory();
        this.data = data;
    }

    /**
     * A command to add a skill to the deck
     * @param argument the second part of the command used as an argument
     * @param state current state of the game
     * @return the next state that should be set
     */
    @Override
    public State execute(String argument, State state) {
        if (state != State.IDLE){
            System.out.println("You can not do that right now");
            return state;
        }
        if (argument == null) {
            System.out.println("Add an argument");
            return state;
        }
        inventory.addDeck(data.getSkill(argument.toLowerCase()));
        return state;
    }
}
