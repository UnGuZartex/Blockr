package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.BlockFunctionality;


/**
 * A class for functional blocks. These are the most common blocks.
 *
 * @author Alpha-team
 */
public class FunctionalBlock extends Block {

    /**
     * Variable referring to the main connector of this block.
     */
    private final MainConnector mainConnector;

    /**
     * Initialise a new functional block with given functionality. The
     * main connector and sub connector of this functional block are
     * created.
     *
     * @param functionality The functionality for this functionality block.
     *
     * @effect Calls the super constructor with given functionality
     * @effect Set the main connector to a new connector which is facing up
     *         and has the socket type.
     * @effect Add a new sub connector to the sub connector list facing down
     *         with the plug type.
     */
    public FunctionalBlock(BlockFunctionality functionality) {
         super(functionality);
         mainConnector = new MainConnector(this, Orientation.FACING_UP, Type.SOCKET);
         getSubConnectors().add(new SubConnector(this, Orientation.FACING_DOWN, Type.PLUG));
    }

    /**
     * Check whether or not this block has a next block.
     *
     * @return True if and only if the sub connector of this block has a
     *         another block connected to it.
     */
    @Override
    public boolean hasNext() {
        return getSubConnectorAt(0).isConnected();
    }

    /**
     * Get the next block of this functional block.
     *
     * @return The block connected to the sub connector of this functional block.
     *
     * @throws IllegalStateException
     *         If this block is terminated
     */
    @Override
    public Block getNext() throws IllegalStateException {
        if (isTerminated()) {
            throw new IllegalStateException("This block is terminated!");
        }
        if (hasNext()) {
            Block nextBlock = getSubConnectors().get(0).getConnectedBlock();
            nextBlock.setReturnToBlock(this.getReturnToBlock());
            return nextBlock;
        }
        return getReturnToBlock();
    }

    /**
     * Get a copy of this functional block.
     *
     * @return A new functional block, but with the functionality of this block
     *         and which is not connected.
     */
    @Override
    public Block clone() {
        return new FunctionalBlock(functionality);
    }

    /**
     * Get the main connector of this functional block.
     *
     * @return The main connector of this functional block.
     */
    @Override
    public MainConnector getMainConnector() {
        return mainConnector;
    }

    /**
     * Get the block at the given index.
     *
     * @param index The index of the block to get.
     *
     * @return The block at the given index in the linked link structure.
     *
     * @throws IllegalArgumentException
     *         If no block can be found at the given index
     */
    @Override
    public Block getBlockAtIndex(int index) throws IllegalArgumentException {
        if (index < 0) {
            return null;
        }
        if (index == 0) {
            return this;
        }
        if (getSubConnectorAt(0).isConnected()) {
            getSubConnectorAt(0).getConnectedBlock().setReturnToBlock(this.getReturnToBlock());
            return getSubConnectorAt(0).getConnectedBlock().getBlockAtIndex(index - 1);
        }
        if (getReturnToBlock() == null) {
            throw new IllegalArgumentException("No block can be find at the given index!");
        }
        return getReturnToBlock().getBlockAtIndex(index - 1);
    }

    /**
     * Get the index of the given block from this block starting.
     *
     * @param block The block to get the index of.
     *
     * @pre The given block may not be connected through the main connector of this block.
     *
     * @return The index of the given block in the structure of this block.
     *
     * @throws IllegalArgumentException
     *         If the given block can't be found
     */
    @Override
    public int getIndexOfBlock(Block block) throws IllegalArgumentException {
        if (block == null) {
            return -1;
        }
        if (block == this) {
            return 0;
        }
        if (getSubConnectorAt(0).isConnected()) {
            getSubConnectorAt(0).getConnectedBlock().setReturnToBlock(this.getReturnToBlock());
            return 1 + getSubConnectorAt(0).getConnectedBlock().getIndexOfBlock(block);
        }
        if (getReturnToBlock() == null) {
            throw new IllegalArgumentException("Can't find the given block!");
        }
        return 1 + getReturnToBlock().getIndexOfBlock(block);
    }
}