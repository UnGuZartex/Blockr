package Controllers;

import GameWorldAPI.GameWorld.Result;
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
     * Notify the listeners that the program has been reset.
     *
     * @effect The listeners are notified about the program reset.
     */
    public void notifyProgramReset() {
        for (ProgramListener listener : listeners) {
            listener.onProgramReset();
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

    public void notifyExecuting(boolean b) {
        for (ProgramListener listener : listeners) {
            listener.onExecuting(b);
        }
    }
}
