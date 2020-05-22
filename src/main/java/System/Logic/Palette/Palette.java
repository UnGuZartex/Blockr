package System.Logic.Palette;

import Controllers.PaletteListener;
import Controllers.ProcedureListener;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.ProcedureBlock;
import System.BlockStructure.Blocks.ProcedureCall;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to keep track of the palette.
 *
 * @invar The blocks in the palette must be valid at all time.
 *        | isValidBlocks(paletteBlocks)
 *
 * @author Alpha-team
 */
public class Palette implements ProcedureListener {

    /**
     * Variable referring to the blocks in the palette.
     */
    private final List<Block> paletteBlocks;
    /**
     * Variable referring to the list of procedure call blocks in this palette.
     */
    private final List<ProcedureCall> procedureCallList = new ArrayList<>();
    /**
     * Variable referring to all the listeners of this palette.
     */
    private final List<PaletteListener> listeners = new ArrayList<>();

    /**
     * Create a new palette with the given blocks as available palette blocks.
     *
     * @param paletteBlocks The given list of blocks.
     *
     * @post the palette blocks are set to the given list of blocks.
     *
     * @throws IllegalArgumentException
     *         If the given blocks are not valid.
     */
    public Palette(List<Block> paletteBlocks) throws IllegalArgumentException {
        if (!isValidBlockList(paletteBlocks)) {
            throw new IllegalArgumentException("The given palette blocks are invalid!");
        }
        this.paletteBlocks = paletteBlocks;
    }

    /**
     * Checks whether the given blocks are valid for a palette.
     *
     * @param blocks The blocks to check.
     *
     * @return True if and only if the given blocks are effective, there is at
     *         least one block in the list and it doesn't contain null.
     */
    public static boolean isValidBlockList(List<Block> blocks) {
        return blocks != null && blocks.size() > 0 && !blocks.contains(null);
    }

    /**
     * Get a new block of the same type as the palette block at the given index in the palette.
     *
     * @param index The index of the block in the palette list to create and return.
     *
     * @return A clone of the block at the given index, such that it seems that the palette blocks
     *         and call list are added as one list.
     *
     * @throws IndexOutOfBoundsException
     *         If the given index is smaller than 0 or greater than the number of blocks in this palette.
     */
    public Block getNewBlock(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= (paletteBlocks.size() + procedureCallList.size())) {
            throw new IndexOutOfBoundsException("The given index for the block to choose is out of bounds!");
        }
        if (index >= paletteBlocks.size()) {
            index %= paletteBlocks.size();
            return procedureCallList.get(index).clone();
        }
        return paletteBlocks.get(index).clone();
    }

    /**
     * Event to call when the procedure is deleted.
     *
     * @param procedureBlock The procedure which is deleted.
     *
     * @effect Delete the caller of the given procedure block.
     * @effect Notify that the procedure is deleted at with the index of the caller.
     */
    @Override
    public void onProcedureDeleted(ProcedureBlock procedureBlock) {
        int index = deleteCaller(procedureBlock);
        notifyProcedureDeleted(index);
    }

    /**
     * Event to call when a procedure is created.
     *
     * @param procedureBlock The procedure which is created.
     *
     * @effect A caller for the given procedure is created.
     * @effect The creation of a new procedure is notified.
     */
    @Override
    public void onProcedureCreated(ProcedureBlock procedureBlock) {
        createCaller(procedureBlock);
        notifyProcedureCreated();
    }

    /**
     * Subscribe a new palette listener.
     *
     * @param listener The listener to subscribe.
     *
     * @effect The given listener is added to the listeners of this palette.
     */
    public void subscribe(PaletteListener listener) {
        listeners.add(listener);
    }

    /**
     * Unsubscribe a palette listener.
     *
     * @param listener The listener to unsubscribe.
     *
     * @effect The given listener is removed from the listeners of this palette.
     */
    public void unsubscribe(PaletteListener listener) {
        listeners.remove(listener);
    }

    /**
     * Create a caller for the given procedure block.
     *
     * @param lastProcedure The procedure to create a caller for.
     *
     * @effect A procedure call is added to the palette for the given procedure.
     */
    private void createCaller(ProcedureBlock lastProcedure) {
        procedureCallList.add(new ProcedureCall(lastProcedure));
    }

    /**
     * Delete the call block for the given procedure block in this palette.
     *
     * @param lastProcedure The procedure to delete the call block for.
     *
     * @effect The call of the given procedure is removed from the palette.
     *
     * @return The index at which the call was found.
     *
     * @throws IllegalArgumentException
     *         If no call block for the given procedure exists in this palette.
     */
    private int deleteCaller(ProcedureBlock lastProcedure) throws IllegalArgumentException {
        int index = 0;
        for (ProcedureCall call : procedureCallList) {
            if (call.getProcedure() == lastProcedure) {
                procedureCallList.get(index).terminate();
                procedureCallList.remove(index);
                return index;
            }
            index++;
        }
        throw new IllegalArgumentException("No call for the given procedure exists!");
    }

    /**
     * Notify that a procedure is deleted.
     *
     * @param index The index of the call of the procedure.
     *
     * @effect Notify all listeners that a procedure is deleted with a call at the given index.
     */
    private void notifyProcedureDeleted(int index) {
        for (PaletteListener listener : new ArrayList<>(listeners)) {
            listener.procedureDeleted(index);
        }
    }

    /**
     * Notify that a procedure is created.
     *
     * @effect Notify all listeners that a procedure is created.
     */
    private void notifyProcedureCreated() {
        for (PaletteListener listener : new ArrayList<>(listeners)) {
            listener.procedureCreated();
        }
    }
}