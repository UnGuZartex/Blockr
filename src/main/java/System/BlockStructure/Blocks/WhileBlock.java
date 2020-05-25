package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.CavityFunctionality;
import System.Logic.ProgramArea.Utility.ExecutionStack;

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
     * @param systemStack The stack to use in the block calculation.
     *
     * @return The block at the index. First is in the cavity counted and
     *         then is under the cavity counted.
     */
    @Override
    public Block getBlockAtIndex(int index, ExecutionStack systemStack) {
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
                systemStack.push(this);
                Block toReturn = nextBlock.getBlockAtIndex(index - 1, systemStack);
                passed = false;
                return toReturn;
            }
            else {
                return super.getBlockAtIndex(index, systemStack);
            }
        }
    }

    /**
     * Get the index of the given block.
     *
     * @param block The block to get the index of.
     * @param systemStack The stack to use in the index calculation.
     *
     * @return The index of the given block. If this block has a cavity, then
     *         is in the cavity looked, otherwise there is in underneath the
     *         block checked.
     */
    public int getIndexOfBlock(Block block, ExecutionStack systemStack) {
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
                systemStack.push(this);
                int toReturn = nextBlock.getIndexOfBlock(block, systemStack);
                passed = false;
                return 1 + toReturn;
            }
            else {
                return super.getIndexOfBlock(block, systemStack);
            }
        }
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

    /**
     * Push the next blocks to execute on the stack.
     *
     * @param stack The stack to push the blocks on.
     *
     * @effect If the functionality evaluates to True, then is first this block pushed and then the cavity,
     *         otherwise are the default blocks pushed to the stack.
     */
    @Override
    public void pushNextBlocks(ExecutionStack stack) {
        if (getFunctionality().getEvaluation()) {
            stack.push(this);
            stack.push(getCavitySubConnector().getConnectedBlock());
        } else {
            super.pushNextBlocks(stack);
        }
    }
}
