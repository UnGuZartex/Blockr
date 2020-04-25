package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.CavityFunctionality;

/**
 * A class for while blocks. These are cavity blocks which have a
 * cavity functionality. The cavity can be repeated multiple times.
 *
 * @author Alpha-team
 */
public class WhileBlock extends CavityBlock {

    private boolean passed;

    /**
     * Initialise a new while block.
     *
     * @effect Calls super constructor.
     */
    public WhileBlock() {
        super(new CavityFunctionality());
    }

    @Override
    public Block getBlockAtIndex(int index) {
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

    public int getIndexOfBlock(Block block) {
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
}
