package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Functionality.BlockFunctionality;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * An abstract class for blocks with a functionality.
 *
 * @author Alpha-team
 */
//TODO VOOR ALLE BLOKKEN COMMENTAAR UPDATEN
public abstract class Block {

    /**
     * Variable referring to the functionality of this block.
     */
    protected BlockFunctionality functionality;
    /**
     * Variable referring to all the sub connectors of this block.
     */
    private final List<SubConnector> subConnectors = new ArrayList<>();
    /**
     * Variable referring to whether or not this block is terminated or not.
     */
    private boolean isTerminated;

    /**
     * Initialise a new block with given functionality
     *
     * @param functionality The functionality of this block.
     *
     * @post The functionality of this block is set to the given functionality.
     */
    protected Block(BlockFunctionality functionality) {
        this.functionality = functionality;
    }

    /**
     * Get the functionality of this block.
     *
     * @return The functionality of this block.
     */
    public BlockFunctionality getFunctionality() {
        return functionality;
    }

    /**
     * Check whether or not this block is terminated or not.
     *
     * @return True if and only if this block is terminated, otherwise false.
     */
    public boolean isTerminated() {
        return isTerminated;
    }

    /**
     * Check whether this block has proper connections.
     *
     * @return True if and only if this block has no following up blocks
     *         or its next block has valid following up blocks.
     */
    public boolean hasProperConnections(Stack<Block> systemStack) {
        return !hasNext() || getNext(systemStack).hasProperConnections(systemStack);
    }

    /**
     * Checks whether or not this block is an illegal extra starting block.
     *
     * @return Always true.
     */
    public boolean isIllegalExtraStartingBlock() {
        return true;
    }

    /**
     * Return whether the block is connected on a main connector.
     *
     * @return true when the block has a main connector and that connector is
     *         connected, false otherwise.
     */
    public boolean isConnectedOnMain() {
        return getMainConnector() != null && getMainConnector().isConnected();
    }

    /**
     * Get the sub connectors of this block.
     *
     * @return The sub connectors of this block.
     */
    protected List<SubConnector> getSubConnectors() {
        return subConnectors;
    }

    /**
     * Get the sub connector of this block at the given index.
     *
     * @param index The index of the sub connector of this block to return.
     *
     * @return The sub connector of this block at the given index.
     */
    public SubConnector getSubConnectorAt(int index) {
        return subConnectors.get(index);
    }

    /**
     * Get the number of sub connectors in this block.
     *
     * @return The number of sub connectors.
     */
    public int getNbSubConnectors() {
        return subConnectors.size();
    }

    /**
     * Reset this block and all its connected blocks.
     *
     * @effect The already ran variable is set to null.
     * @effect The connected blocks are reset.
     *
     * @throws IllegalStateException
     *         If this block is terminated
     */
    public void reset() throws IllegalStateException {
        if (isTerminated()) {
            throw new IllegalStateException("This block is terminated!");
        }
        //setReturnToBlock(null);
        for(int i = 0; i < getSubConnectors().size(); i++) {
            if (getSubConnectors().get(i).isConnected()) {
                Block connectBlock = getSubConnectors().get(i).getConnectedBlock();
                connectBlock.reset();
            }
        }
    }

    /**
     * Terminate this block.
     *
     * @effect The blocks connected onto this blocks subconnectors are terminated.
     *
     * @post This block is terminated.
     */
    public void terminate() {
        isTerminated = true;
        for (SubConnector subConnector : subConnectors) {
            if (subConnector.isConnected()) {
                subConnector.getConnectedBlock().terminate();
            }
        }
    }


    /**
     * Get the main connector of this block.
     *
     * @return The main connector of this block.
     */
    public abstract MainConnector getMainConnector();

    /**
     * Check whether or not this block has a next block.
     *
     * @return True if and only if this block has a next block.
     */
    public abstract boolean hasNext();

    /**
     * Get the block which should be executed after this block.
     *
     * @return The block which should be executed after this block.
     */
    public abstract Block getNext(Stack<Block> systemStack);

    /**
     * Clone this block.
     *
     * @return A new block with the with the same properties as this block.
     */
    public abstract Block clone();

    /**
     * Get the block at the given index.
     *
     * @param index The index to get the block from.
     *
     * @return The block which is at the given index in the structure of this block.
     */
    public abstract Block getBlockAtIndex(int index, Stack<Block> systemStack);

    /**
     * Get the index of the given block.
     *
     * @param block The block to get the index off.
     *
     * @return The index of the given block in the structure of this block.
     */
    public abstract int getIndexOfBlock(Block block, Stack<Block> systemStack);
}