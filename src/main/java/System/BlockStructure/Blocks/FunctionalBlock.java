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
     * Variable referring to the mainconnector of this block.
     */
    private final MainConnector mainConnector;

    /**
     * Initialise a new functional block with given functionality. The
     * main connector and sub connector of this functional block are
     * created.
     *
     * @param functionality The functionality of this functionality block.
     */
    public FunctionalBlock(BlockFunctionality functionality) {
        super(functionality);
         mainConnector = new MainConnector(this, Orientation.FACING_UP, Type.SOCKET);

         getSubConnectors().add(new SubConnector("SUB_1", this, Orientation.FACING_DOWN, Type.PLUG));
    }

    /**
     * Check whether or not this block has a next block.
     *
     * @return True if and only if the sub connector of this block has a
     *         another block connected to it.
     */
    @Override
    public boolean hasNext() {
        return getSubConnectors().get(0).isConnected();
    }

    /**
     * Get the next block of this functional block.
     *
     * @return The block connected to the sub connector of this functional block.
     */
    @Override
    public Block getNext() {
        return getSubConnectors().get(0).getConnectedBlock();
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
     * Returns the next executable block if the next block would be null
     *
     * @return if the block has no upper connection it this block gets returned, otherwise the upper
     * connection is followed such that the closest cavity can be found.
     */
    @Override
    public Block getNextIfNone() {
        if (!mainConnector.isConnected()) {
            return this;
        }
        return mainConnector.getConnectedBlock().getNextIfNone();
    }
}
