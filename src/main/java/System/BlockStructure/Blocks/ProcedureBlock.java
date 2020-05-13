package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.DummyFunctionality;

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
        passed = true;
        boolean result = super.hasProperConnections();
        passed = false;
        return result;
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
        return getSubConnectorAt(0).isConnected();
    }

    /**
     * Get the next block.
     *
     * @return If this block has a next block, then that block is returned and the return to
     *         block of that block is set to the return to block of this block. If this block
     *         does not have a next block, then the return to block of this block is returned.
     */
    @Override
    public Block getNext() {
        if (hasNext()) {
            Block nextBlock = getSubConnectorAt(0).getConnectedBlock();
            nextBlock.setReturnToBlock(getReturnToBlock());
            return nextBlock;
        }
        return getReturnToBlock();
    }

    /**
     * Clone this block.
     *
     * @return A new procedure block which is not connected.
     */
    @Override
    public Block clone() {
        return new ProcedureBlock();
    }

    /**
     * Get the block at the given index.
     *
     * @param index The index to get the block from.
     *
     * @return The block at the given index.
     *
     * @throws IllegalArgumentException
     *         If the given index is smaller than 0.
     * @throws IllegalArgumentException
     *         If no block can be found at the given index.
     */
    @Override
    public Block getBlockAtIndex(int index) throws IllegalArgumentException {
        if (index < 0) {
            throw new IllegalArgumentException("The given index is invalid!");
        }
        if (index == 0) {
            return this;
        }
        if (getSubConnectorAt(0).isConnected()) {
            getSubConnectorAt(0).getConnectedBlock().setReturnToBlock(getReturnToBlock());
            passed = true;
            Block toReturn = getSubConnectorAt(0).getConnectedBlock().getBlockAtIndex(index - 1);
            passed = false;
            return toReturn;
        }
        if (getReturnToBlock() == null) {
            throw new IllegalArgumentException("No block can be found at the given index!");
        }
        return getReturnToBlock().getBlockAtIndex(index - 1);
    }

    /**
     * Get the index of the given block from this block.
     *
     * @param block The block to get the index off.
     *
     * @return The index of the given block.
     *
     * @throws IllegalArgumentException
     *         If the given block is null.
     * @throws IllegalArgumentException
     *         If the given block can't be found.
     */
    @Override
    public int getIndexOfBlock(Block block) throws IllegalArgumentException {
        if (block == null) {
            throw new IllegalArgumentException("The given block is not effective!");
        }
        if (block == this) {
            return 0;
        }
        if (getSubConnectorAt(0).isConnected()) {
            getSubConnectorAt(0).getConnectedBlock().setReturnToBlock(getReturnToBlock());
            passed = true;
            int toReturn = 1 + getSubConnectorAt(0).getConnectedBlock().getIndexOfBlock(block);
            passed = false;
            return toReturn;
        }
        if (getReturnToBlock() == null) {
            throw new IllegalArgumentException("The given block can't be found!");
        }
        return 1 + getReturnToBlock().getIndexOfBlock(block);
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
     * Check whether or not this procedure is passed.
     *
     * @return True if this procedure is passed.
     */
    public boolean isPassed() {
        return passed;
    }
}
