package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.ConditionalBlockFunctionality;

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
    protected final MainConnector mainConnector;

    /**
     * Initialise a new conditional block with given functionality.
     *
     * @param functionality The functionality of this conditional block.
     * @param <B> The type of block the functionality should belong to.
     */
    protected <B extends ConditionalBlock> ConditionalBlock(ConditionalBlockFunctionality<B> functionality) {
        super(functionality);
        mainConnector = new MainConnector(this, Orientation.FACING_LEFT, Type.PLUG);
        functionality.setBlock((B) this);
    }

    /**
     * Check whether or not this conditional block has a next block.
     *
     * @return False cause a conditional block has no next block.
     */
    @Override
    public boolean hasNext() {
        return false;
    }

    /**
     * Get the next block of a conditional block.
     *
     * @return Null cause a conditional block has no next block in general.
     */
    @Override
    public Block getNext() {
        return null;
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
     * Return to the closest cavity to execute.
     *
     * @return Null cause a conditional block is not contained into
     *         a cavity block.
     */
    @Override
    public Block getNextIfNone() {
        return null;
    }

    /**
     * Checks whether or not this conditional block has proper connections.
     *
     * @return False if the main connector is not connected, otherwise return
     *         if parent block of this conditional block has a proper connections.
     */
    @Override
    public boolean hasProperConnections() {
        if (!getMainConnector().isConnected()) {
            return false;
        }
        return super.hasProperConnections();
    }
}
