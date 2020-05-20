package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.DummyFunctionality;

import java.util.Stack;

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
        if (hasNext()) {
            passed = true;
            boolean result = super.hasProperConnections();
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
        return new ProcedureBlock();
    }

    /**
     * Get the block at the given index.
     *
     * @param index The index to get the block from.
     *
     * @return The block at the given index.
     */
    @Override
    public Block getBlockAtIndex(int index, Stack<Block> systemStack) {
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
            if (nextBlock == null) {
                return null;
            }
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
    public int getIndexOfBlock(Block block, Stack<Block> systemStack) {
        if (block == null) {
            return -1;
        }
        if (block == this) {
            return 0;
        }
        if (getSubConnectorAt(0).isConnected()) {
            passed = true;
            int toReturn = 1 + getSubConnectorAt(0).getConnectedBlock().getIndexOfBlock(block, systemStack);
            passed = false;
            return toReturn;
        }
        if (!systemStack.isEmpty()) {
            Block nextBlock = systemStack.pop();
            if (nextBlock == null) {
                return -1;
            }
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
     * Check whether or not this procedure is passed.
     *
     * @return True if this procedure is passed.
     */
    public boolean isPassed() {
        return passed;
    }

    @Override
    public void pushNextBlocks(Stack<Block> stack) {
        if (hasNext()) {
            stack.push(getSubConnectors().get(0).getConnectedBlock());
        }
    }
}
