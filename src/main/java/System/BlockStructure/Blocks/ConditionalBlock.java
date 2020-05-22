package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.ConditionalBlockFunctionality;
import System.Logic.ProgramArea.ExecutionStack;

/**
 * An abstract class for conditional blocks. This is a block with a conditional
 * functionality.
 *
 * @author Alpha-team
 */
public abstract class ConditionalBlock extends Block {

    /**
     * Variable referring to the main connector of this conditional block.
     */
    private final MainConnector mainConnector;

    /**
     * Initialise a new conditional block with given functionality.
     *
     * @param functionality The functionality of this conditional block.
     * @param <B> The type of block the functionality should belong to.
     *
     * @effect Calls super constructor with given functionality.
     * @effect Set the main connector of this conditional block to a new connector
     *         facing left and with type plug.
     * @effect Set the block of the given functionality to this block.
     */
    protected <B extends ConditionalBlock> ConditionalBlock(ConditionalBlockFunctionality<B> functionality) {
        super(functionality);
        mainConnector = new MainConnector(this, Orientation.FACING_LEFT, Type.PLUG);
        functionality.setBlock((B) this);
    }

    /**
     * Check whether this conditional block has a next block.
     *
     * @return False cause a conditional block has no next block.
     */
    @Override
    public boolean hasNext() {
        return false;
    }

    /**
     * Get the main connector of this conditional block.
     *
     * @return The main connector of this conditional block.
     */
    @Override
    public MainConnector getMainConnector() {
        return mainConnector;
    }

    /**
     * Checks whether this conditional block has proper connections.
     *
     * @return False if the main connector is not connected, otherwise return
     *         if parent block of this conditional block has the proper connections.
     */
    @Override
    public boolean hasProperConnections() {
        if (!getMainConnector().isConnected()) {
            return false;
        }
        return super.hasProperConnections();
    }

    /**
     * Get the block at the given index.
     *
     * @param index The index to get the block at.
     * @param systemStack The stack to use in the block calculation.
     *
     * @throws IllegalStateException
     *         Always, this method may not be called for conditional blocks.
     */
    @Override
    public Block getBlockAtIndex(int index, ExecutionStack systemStack) throws IllegalStateException {
        throw new IllegalStateException("This method may not be called for blocks of " + this.getClass() + "!");
    }

    /**
     * Get the index of the given block in the structure of this block.
     *
     * @param block The block to get the index off.
     * @param systemStack The stack to use in the index calculation.
     *
     * @throws IllegalStateException
     *         Always, this method may not be called for conditional blocks.
     */
    @Override
    public int getIndexOfBlock(Block block,  ExecutionStack systemStack) throws IllegalStateException {
        throw new IllegalStateException("This method may not be called for blocks of " + this.getClass() + "!");
    }

    /**
     * Push the next blocks on the given stack.
     *
     * @param stack The stack to push the blocks on.
     *
     * @throws IllegalStateException
     *         Always, this method may not be called for conditional blocks.
     */
    @Override
    public void pushNextBlocks(ExecutionStack stack) throws IllegalStateException {
        throw new IllegalStateException("Invalid call of pushing to stack in conditional!");
    }
}
