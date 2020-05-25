package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.CavityFunctionality;
import System.Logic.ProgramArea.Utility.ExecutionStack;

/**
 * A class for if blocks. These are cavity blocks which have a
 * cavity functionality. The cavity can only be ran 1 time.
 *
 * @author Alpha-team
 */
public class IfBlock extends CavityBlock {

    /**
     * Initialise a new if block.
     *
     * @effect Calls super constructor.
     */
    public IfBlock() {
        super(new CavityFunctionality());
    }

    /**
     * Get a clone of this block.
     *
     * @param index the index of the block to get.
     * @param systemStack The stack to use in the block calculation.
     *
     * @return A new if block with a copy of the current functionality and which is not
     *         connected to any block.
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
            if (cavitySubConnector.isConnected()) {
                Block nextBlock = getCavitySubConnector().getConnectedBlock();
                if (getSubConnectorAt(0).isConnected()) {
                    systemStack.push(getSubConnectorAt(0).getConnectedBlock());
                }
                return nextBlock.getBlockAtIndex(index - 1, systemStack);
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
    @Override
    public int getIndexOfBlock(Block block, ExecutionStack systemStack)  {
        if (block == null) {
            return -1;
        }
        if (block == this) {
            return 0;
        }
        else {
            if (cavitySubConnector.isConnected()) {
                Block nextBlock = getCavitySubConnector().getConnectedBlock();
                if (getSubConnectorAt(0).isConnected()) {
                    systemStack.push(getSubConnectorAt(0).getConnectedBlock());
                }
                return 1 + nextBlock.getIndexOfBlock(block, systemStack);
            }
            else {
                return super.getIndexOfBlock(block, systemStack);
            }
        }
    }

    /**
     * Clone this block.
     *
     * @return A new if block which is not connected.
     */
    @Override
    public Block clone() {
        return new IfBlock();
    }

    /**
     * Push the correct blocks to the given stack.
     *
     * @param stack The stack to push the blocks on.
     *
     * @effect First execute the super method, and if the functionality evaluates to True,
     *         then is the cavity also added to the stack.
     *
     */
    @Override
    public void pushNextBlocks(ExecutionStack stack) {
        super.pushNextBlocks(stack);
        if (getFunctionality().getEvaluation()) {
            stack.push(getCavitySubConnector().getConnectedBlock());
        }
    }
}
