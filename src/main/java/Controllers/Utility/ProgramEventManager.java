package Controllers.Utility;

import Controllers.ListenerInterfaces.ProgramListener;
import GameWorldAPI.GameWorld.Result;
import java.util.ArrayList;
import java.util.List;

/**
 * A class used as an event manager for the program game/program state.
 *
 * @author Alpha-team
 */
public class ProgramEventManager {

    /**
     * Variable referring to the listeners of this program observer
     */
    private final List<ProgramListener> listeners = new ArrayList<>();

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
     *
     * @param listener the given program listener
     *
     * @post The given listener is removed from the current listeners.
     */
    public void unsubscribe(ProgramListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify the listeners that the game is over.
     * @param result the result of the game
     *
     * @effect The listeners are notified about the new game state.
     */
    public void notifyGameFinished(Result result) {
        for (ProgramListener listener : listeners) {
            listener.onGameFinished(result);
        }
    }

    /**
     * Notify the listeners that the program is currently in its
     * default state.
     *
     * @effect The listeners are notified about the program state.
     */
    public void notifyProgramInDefaultState() {
        for (ProgramListener listener : listeners) {
            listener.onProgramDefaultState();
        }
    }

    /**
     * Notify the listeners that the program is invalid.
     *
     * @effect The listeners are notified about the warning.
     */
    public void notifyProgramInvalid() {
        for (ProgramListener listener : listeners) {
            listener.onProgramInvalid();
        }
    }

    /**
     * Notify the listeners that there are too many programs in the program area.
     *
     * @effect The listeners are notified about the warning.
     */
    public void notifyTooManyPrograms() {
        for (ProgramListener listener : listeners) {
            listener.onTooManyPrograms();
        }
    }
}
