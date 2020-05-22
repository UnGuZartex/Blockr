package System.BlockStructure.Blocks;

import Controllers.ProcedureListener;
import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.DummyFunctionality;
import System.Logic.ProgramArea.ExecutionStack;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for procedure blocks.
 *
 * @author Alpha-team
 */
public class ProcedureBlock extends Block {

    /**
     * A boolean to check whether or not this procedure has been passed.
     */
    private boolean passed;

    /**
     * Variable referring to all the listeners of this block.
     */
    private final List<ProcedureListener> listeners = new ArrayList<>();

    /**
     * Initialise a new Procedure Block.
     *
     * @effect Calls super constructor with a dummy functionality
     * @effect Adds a sub connector to the sub connector list which is facing down and has type plug.
     */
    public ProcedureBlock() {
        super(new DummyFunctionality());
        getSubConnectors().add(new SubConnector(this, Orientation.FACING_DOWN, Type.PLUG));
    }

    /**
     * Check if this procedure has proper connections.
     *
     * @return The result of the super method hasProperConnections while passed is True.
     */
    @Override
    public boolean hasProperConnections() {
        if (hasNext()) {
            passed = true;
            boolean result = getSubConnectorAt(0).getConnectedBlock().hasProperConnections();
            passed = false;
            return result;
        }
        return true;
    }

    /**
     * Get the main connector of this block.
     *
     * @return Null is returned.
     */
    @Override
    public MainConnector getMainConnector() {
        return null;
    }

    /**
     * Check whether or not there is a next block for this block.
     *
     * @return True if and only if the sub connector is connected.
     */
    @Override
    public boolean hasNext() {
        return !passed && getSubConnectorAt(0).isConnected();
    }

    /**
     * Clone this block.
     *
     * @return A new procedure block which is not connected.
     */
    @Override
    public Block clone() {
        ProcedureBlock toReturn = new ProcedureBlock();
        for (ProcedureListener listener:listeners) {
            toReturn.subscribe(listener);
        }
        notifyProcedureCreated(toReturn);
        return toReturn;
    }

    /**
     * TODO func
     */
    @Override
    public void terminate() {
        notifyProcedureDeleted();
        super.terminate();
    }

    /**
     * Get the block at the given index.
     *
     * @param index The index to get the block from.
     *
     * @return The block at the given index.
     */
    @Override
    public Block getBlockAtIndex(int index, ExecutionStack systemStack) {
        if (index < 0) {
            return null;
        }
        if (index == 0) {
            return this;
        }
        if (hasNext()) {
            passed = true;
            Block toReturn = getSubConnectorAt(0).getConnectedBlock().getBlockAtIndex(index - 1, systemStack);
            passed = false;
            return toReturn;
        }
        if (!systemStack.isEmpty()) {
            Block nextBlock = systemStack.pop();
            return nextBlock.getBlockAtIndex(index - 1, systemStack);
        }
        return null;
    }

    /**
     * Get the index of the given block from this block.
     *
     * @param block The block to get the index off.
     *
     * @return The index of the given block.
     */
    @Override
    public int getIndexOfBlock(Block block, ExecutionStack systemStack) {
        if (block == null) {
            return -1;
        }
        if (block == this) {
            return 0;
        }
        if (hasNext()) {
            passed = true;
            int toReturn = 1 + getSubConnectorAt(0).getConnectedBlock().getIndexOfBlock(block, systemStack);
            passed = false;
            return toReturn;
        }
        if (!systemStack.isEmpty()) {
            Block nextBlock = systemStack.pop();
            return 1 + nextBlock.getIndexOfBlock(block, systemStack);
        }
        return -1;
    }

    /**
     * Checks whether or not this block is an illegal starting block.
     *
     * @return False is returned.
     */
    @Override
    public boolean isIllegalExtraStartingBlock() {
        return false;
    }

    /**
     * Subscribe the given listener as a listener of this block.
     *
     * @param listener The listener to subscribe.
     */
    public void subscribe(ProcedureListener listener) {
        listeners.add(listener);
    }

    /**
     * Unsubscribe the given listener as a listener of this block.
     *
     * @param listener The listener to unsubscribe.
     */
    public void unSubscribe(ProcedureListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify that the procedure is deleted.
     * TODO UPDATE ALL COMMENTS UNDER THIS
     */
    private void notifyProcedureDeleted() {
        for (ProcedureListener listener : new ArrayList<>(listeners)) {
            listener.onProcedureDeleted(this);
        }
    }

    private void notifyProcedureCreated(ProcedureBlock created) {
        for (ProcedureListener listener : new ArrayList<>(listeners)) {
            listener.onProcedureCreated(created);
        }
    }
}
