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
     *
     * @effect Calls super constructor with given functionality
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
        return getSubConnectors().get(0).isConnected();
    }

    /**
     * Get the next block of this functional block.
     *
     * @return The block connected to the sub connector of this functional block.
     */
    @Override
    public Block getNext() {
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
}
