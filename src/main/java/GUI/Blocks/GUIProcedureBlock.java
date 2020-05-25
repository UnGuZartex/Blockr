package GUI.Blocks;

import Controllers.ControllerClasses.BlockHandlerController;
import Controllers.ControllerClasses.ConnectionController;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for gui procedure blocks. This is a gui cavity block but
 * with no conditional sub connector and a connector below.
 *
 * @author Alpha-team
 */
public class GUIProcedureBlock extends GUISimpleCavityBlock {

    /**
     * Variable referring to the different procedure indexes which are already
     * taken. If entry i is true, then the index i is taken. If i exceeds the
     * length of the list, then i is not taken.
     */
    public static final List<Boolean> takenProcedureNumbers = new ArrayList<>();
    /**
     * Variable referring to the current priority of this block, which is initially
     * the max value.
     */
    private static int currentPriority = Integer.MAX_VALUE;
    /**
     * Variable referring to the procedure number of this procedure.
     */
    private int procedureNr;

    /**
     * Initialise a new procedure block with given coordinates and procedure number.
     *
     * @param x The x coordinate for this block.
     * @param y The y coordinate for this block.
     * @param procedureNr The procedure number for this block.
     *
     * @post The procedure number is set to the given number.
     * @post The priority is set to the current priority and it is decremented.
     *
     * @effect Calls super constructor with given coordinates and the name "Def 'procedureNr'"
     */
    public GUIProcedureBlock(int x, int y, int procedureNr){
        super("Def " + procedureNr, x, y);
        this.procedureNr = procedureNr;
        priority = currentPriority--;
    }

    /**
     * Initialise a new procedure block with given coordinates and procedure number.
     *
     * @param name The name of this procedure block.
     * @param x The x coordinate for this block.
     * @param y The y coordinate for this block.
     *
     * @post The priority is set to the current priority and it is decremented.
     *
     * @effect Calls super constructor with given coordinates and name.
     */
    public GUIProcedureBlock(String name, int x, int y) {
        super(name, x, y);
        priority = currentPriority--;
    }

    /**
     * Disconnect the main connector of this block.
     * Disconnecting the main connector has no effect.
     */
    @Override
    public void disconnectMainConnector() { }

    /**
     * Remove this block from between its main connector and its sub connector below.
     *
     * @param connectionController The connection controller to execute the removal with.
     * @param blockHandlerController The block handler controller to execute the removal with.
     *
     * @throws IllegalStateException
     *         Because the procedure block can't be removed between blocks."
     */
    @Override
    public void removeInBetween(ConnectionController connectionController, BlockHandlerController blockHandlerController) throws IllegalStateException{
        throw new IllegalStateException("Procedure block can't be removed between blocks.");
    }

    /**
     * Clone this procedure.
     *
     * @return A new gui block with the same coordinates and a new procedure block.
     */
    @Override
    public GUIBlock clone() {
        return new GUIProcedureBlock(x, y, getNewProcedureNumber());
    }

    /**
     * Terminate this block.
     *
     * @effect This block is terminated.
     * @effect The procedure number of this block is set to not taken if this is not the initial procedure block.
     * @effect If the procedure number of this block is the size of the total number of taken numbers, then it is removed.
     */
    @Override
    public void terminate() {
        super.terminate();

        if (procedureNr > 0) {
            takenProcedureNumbers.set(procedureNr - 1, false);

            if (procedureNr == takenProcedureNumbers.size()) {
                takenProcedureNumbers.remove(takenProcedureNumbers.size() - 1);
            }
        }
    }

    /**
     * Get a new procedure number.
     *
     * @return The first procedure number which is not taken yet. This is done
     *         using the taken procedure numbers list.
     */
    private int getNewProcedureNumber() {
        int currentIndex = -1;

        for (int i = 0; i < takenProcedureNumbers.size(); i++) {
            if (!takenProcedureNumbers.get(i)) {
                takenProcedureNumbers.set(i, true);
                currentIndex = i;
                break;
            }
        }

        if (currentIndex == -1) {
            takenProcedureNumbers.add(true);
            return takenProcedureNumbers.size();
        }

        return currentIndex + 1;
    }
}
