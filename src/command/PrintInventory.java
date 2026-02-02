package command;

import game.CurrentData;
import game.Inventory;
import game.State;

public class PrintInventory implements Command{
    private Inventory inventory;

    public PrintInventory(CurrentData cData){
        inventory = cData.getInventory();
    }

    /**
     * Prints Inventory
     * @param argument the second part of the command used as an argument
     * @param state    current state of the game
     * @return the new state
     */
    @Override
    public State execute(String argument, State state) {
        if (state == State.IDLE) {
            System.out.println("You can not do that right now");
            return state;
        }
        System.out.println(inventory);
        return state;
    }
}
