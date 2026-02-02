package command;

import game.CurrentData;
import game.GameData;
import game.Inventory;
import game.State;

public class Equip implements Command{
    private Inventory inventory;
    private GameData data;

    public Equip(CurrentData cData, GameData data){
        inventory = cData.getInventory();
        this.data = data;
    }

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
