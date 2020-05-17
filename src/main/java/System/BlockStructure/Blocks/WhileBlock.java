package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.CavityFunctionality;

import java.util.Stack;

/**
 * A class for while blocks. These are cavity blocks which have a
 * cavity functionality. The cavity can be repeated multiple times.
 *
 * @author Alpha-team
 */
public class WhileBlock extends CavityBlock {

    /**
     * A variable referring to whether or not this block has been passed while
     * looking for the block by index.
     */
    private boolean passed;

    /**
     * Initialise a new while block.
     *
     * @effect Calls super constructor.
     */
    public WhileBlock() {
        super(new CavityFunctionality());
    }

    /**
     * Get the block at the given index.
     *
     * @param index The index of the block to get.
     *
     * @return The block at the index. First is in the cavity counted and
     *         then is under the cavity counted.
     */
    @Override
    public Block getBlockAtIndex(int index) {
        if (index < 0) {
            return null;
        }
        if (index == 0) {
            return this;
        }
        else {
            if (cavitySubConnector.isConnected() && !passed) {
                passed = true;
                Block nextBlock = getCavitySubConnector().getConnectedBlock();
                nextBlock.setReturnToBlock(getNewReturnBlock());
                Block toReturn = nextBlock.getBlockAtIndex(index - 1);
                passed = false;
                return toReturn;
            }
            else {
                return super.getBlockAtIndex(index);
            }
        }
    }

    /**
     * Get the index of the given block.
     *
     * @param block The block to get the index of.
     *
     * @return The index of the given block. If this block has a cavity, then
     *         is in the cavity looked, otherwise there is in underneath the
     *         block checked.
     */
    public int getIndexOfBlock(Block block) {
        if (block == null) {
           return -1;
        }
        if (block == this) {
            return 0;
        }
        else {
            if (cavitySubConnector.isConnected() && !passed) {
                passed = true;
                Block nextBlock = getCavitySubConnector().getConnectedBlock();
                nextBlock.setReturnToBlock(getNewReturnBlock());
                int toReturn = nextBlock.getIndexOfBlock(block);
                passed = false;
                return 1 + toReturn;
            }
            else {
                return super.getIndexOfBlock(block);
            }
        }
    }

    /**
     * Get the new return to block for this block.
     *
     * @return This block.
     */
    @Override
    protected Block getNewReturnBlock() {
        return this;
    }

    /**
     * Get a clone of this block.
     *
     * @return A new while block with a copy of the current functionality and which is not
     *         connected to any block.
     */
    @Override
    public Block clone() {
        return new WhileBlock();
    }

    @Override
    public void pushNextBlocks(Stack<Block> stack) {
        if (getFunctionality().getEvaluation()) {
            stack.push(this);
            if (getCavitySubConnector().isConnected()) {
                stack.push(getCavitySubConnector().getConnectedBlock());
            }
        } else if (getSubConnectorAt(0).isConnected()) {
            stack.push(getSubConnectorAt(0).getConnectedBlock());
        }
    }
}
