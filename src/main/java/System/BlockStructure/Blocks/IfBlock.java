package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.CavityFunctionality;

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
     * Get the new return to block.
     *
     * @return If a block below this if is connected, that block, otherwise
     *         the default return to block.
     */
    @Override
    protected Block getNewReturnBlock() {
        if (getSubConnectorAt(0).isConnected()) {
            return getSubConnectorAt(0).getConnectedBlock();
        }
        return getReturnToBlock();
    }

    /**
     * Get a clone of this block.
     *
     * @param index the index of the block to get.
     *
     * @return A new if block with a copy of the current functionality and which is not
     *         connected to any block.
     */
    @Override
    public Block getBlockAtIndex(int index) {
        if (index == 0) {
            return this;
        }
        else {
            if (cavitySubConnector.isConnected()) {
                Block nextBlock = getCavitySubConnector().getConnectedBlock();
                nextBlock.setReturnToBlock(getNewReturnBlock());
                return nextBlock.getBlockAtIndex(index - 1);
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
    @Override
    public int getIndexOfBlock(Block block) {
        if (block == this) {
            return 0;
        }
        else {
            if (cavitySubConnector.isConnected()) {
                Block nextBlock = getCavitySubConnector().getConnectedBlock();
                nextBlock.setReturnToBlock(getNewReturnBlock());
                return 1 + nextBlock.getIndexOfBlock(block);
            }
            else {
                return super.getIndexOfBlock(block);
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
}
