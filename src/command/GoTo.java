package command;

import game.CurrentData;
import game.GameData;
import game.State;
import thoseBoringClassesThatExistJustToStoreThings.Location;

/**
 * A command to move to a location.
 * @author Patrik Novotný.
 */
public class GoTo implements Command{
    CurrentData cData;
    GameData data;

    public GoTo(CurrentData currentData, GameData data){
        cData = currentData;
        this.data = data;
    }

    /**
     * A command to move to a location.
     * @param argument the second part of the command used as an argument
     * @param state    current state of the game
     * @return the next state that should be set.
     */
    @Override
    public State execute(String argument, State state) {
        if (state == State.IDLE){
            Location requestedLocation = data.getLocation(argument);
            if(requestedLocation != null){
                if(requestedLocation.getId() <= cData.getUnlockedLocation()){
                    cData.setCurrentLocation(requestedLocation.getId());
                }else if(argument.equals("next")){
                    cData.setCurrentLocation(cData.getUnlockedLocation());
                }else System.out.println("You have not reached that location yet");
            }else System.out.println("That location does not exist");
        }else System.out.println("You can not do that right now");
        return State.DIALOGUE;
    }
}
