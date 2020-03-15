package Controllers;

import System.GameWorld.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * A class used as an observer for notifying its listeners about
 * events of the game/program state.
 *
 * @author Alpha-team
 */
public class ProgramObserver {

    /**
     * Variable referring to the listeners of this program observer
     */
    private List<ProgramListener> listeners = new ArrayList<>();

    /**
     * Subscribe a given program listener to this observer
     *
     * @param listener the given program listener
     *
     * @post The given listener is added to the current listeners.
     */
    public void subscribe(ProgramListener listener) {
        listeners.add(listener);
    }

    /**
     * Unsubscribe a given program listener from this observer
     * @param listener the given program listener
     *
     * @post The given listener is removed from the current listeners.
     */
    public void unsubscribe(ProgramListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify the listeners that the program has finished and the game is won.
     *
     @effect The listeners are notified about the new game state.
     */
    public void notifyGameWon() {
        for (ProgramListener listener : listeners) {
            listener.onGameWon();
        }
    }

    /**
     * Notify the listeners that the player has lost the game.
     *
     @effect The listeners are notified about the new game state.
     */
    public void notifyGameLost() {
        for (ProgramListener listener : listeners) {
            listener.onGameLost();
        }
    }
}
