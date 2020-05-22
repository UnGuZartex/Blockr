package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Functionality.BlockFunctionality;
import System.Logic.ProgramArea.ExecutionStack;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class for blocks with a functionality.
 *
 * @author Alpha-team
 */
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
    public boolean hasProperConnections() {
        return !hasNext() || getSubConnectorAt(0).getConnectedBlock().hasProperConnections();
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
     * Terminate this block.
     *
     * @post This block is terminated.
     */
    public void terminate() {
        isTerminated = true;
    }

    /**
     * Push the next blocks on the stack.
     *
     * @param stack The stack to push the blocks on.
     *
     * @effect The block at the first sub connector is pushed.
     */
    public void pushNextBlocks(ExecutionStack stack) {
        stack.push(getSubConnectorAt(0).getConnectedBlock());
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
     * Clone this block.
     *
     * @return A new block with the with the same properties as this block.
     */
    public abstract Block clone();

    /**
     * Get the block at the given index.
     *
     * @param index The index to get the block from.
     * @param systemStack The stack to use in the block calculation.
     *
     * @return The block which is at the given index in the structure of this block.
     */
    public abstract Block getBlockAtIndex(int index, ExecutionStack systemStack);

    /**
     * Get the index of the given block.
     *
     * @param block The block to get the index off.
     * @param systemStack The stack to use in the index calculation.
     *
     * @return The index of the given block in the structure of this block.
     */
    public abstract int getIndexOfBlock(Block block, ExecutionStack systemStack);
}