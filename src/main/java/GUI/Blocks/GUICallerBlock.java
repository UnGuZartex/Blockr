package GUI.Blocks;

import Controllers.CallListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for GUI call blocks, which call procedures. GUI call blocks can listen
 * to other call blocks.
 *
 * @invar Each GUI call block must have a valid procedure number at all time.
 *        | isValidProcedureNr(procedureNr)
 *
 * @author Alpha-team
 */
public class GUICallerBlock extends GUIFunctionalBlock implements CallListener {

    /**
     * The procedure number of this procedure call.
     */
    private final int procedureNr;
    /**
     * The list of call listeners which listen to this call.
     */
    private final List<CallListener> listeners = new ArrayList<>();

    /**
     * Create a new GUI caller block with a given procedure number
     * and coordinates.
     *
     * @param x The x coordinate for this block.
     * @param y The y coordinate for this block.
     * @param procedureNr The procedure number for this call.
     *
     * @post The procedure number of this call is set to the given procedure number.
     *
     * @effect Calls super constructor with given parameters.
     *
     * @throws IllegalArgumentException
     *         If the given procedure number is not valid.
     */
    public GUICallerBlock(int x, int y, int procedureNr) throws IllegalArgumentException {
        super("Call " + procedureNr, x, y);
        if (!isValidProcedureNr(procedureNr)) {
            throw new IllegalArgumentException("The given procedure number is invalid!");
        }

        this.procedureNr = procedureNr;
    }

    /**
     * Check whether the given procedure number is valid.
     *
     * @param procedureNr The procedure number to check.
     *
     * @return True if and only if the given procedure number is greater then 0.
     */
    public static boolean isValidProcedureNr(int procedureNr) {
        return procedureNr > 0;
    }

    /**
     * Unsubscribe the given listener from this call.
     *
     * @param listener The listener to unsubscribe.
     *
     * @effect The given listener is removed as listener to this call.
     */
    public void unsubscribe(CallListener listener) {
        listeners.remove(listener);
    }

    /**
     * Subscribe the given listener to this call.
     *
     * @param listener The listener to subscribe.
     *
     * @effect The given listener is added as listener to this call.
     */
    public void subscribe(CallListener listener) {
        listeners.add(listener);
    }

    /**
     * Terminate this call block.
     *
     * @effect This block is terminated.
     * @effect A notification is made that the procedure is deleted.
     */
    @Override
    public void terminate() {
        super.terminate();
        notifyProcedureDeleted();
    }

    /**
     * Event to call when the procedure is deleted.
     *
     * @effect This block is terminated.
     */
    @Override
    public void onProcedureDeleted() {
        terminate();
    }

    /**
     * Clone this block.
     *
     * @return A new call block with the same coordinates and procedure number, the clone is subscirbed
     *         as listener to this block.
     */
    @Override
    public GUIBlock clone() {
        GUICallerBlock toReturn = new GUICallerBlock(x, y, procedureNr);
        this.subscribe(toReturn);
        return toReturn;
    }

    /**
     * Notify that the procedure is deleted.
     *
     * @effect For each listener is the event on procedure deleted called.
     */
    private void notifyProcedureDeleted() {
        for (CallListener listener : new ArrayList<>(listeners)) {
            listener.onProcedureDeleted();
        }
    }
}
