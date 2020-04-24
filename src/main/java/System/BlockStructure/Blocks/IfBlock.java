package System.BlockStructure.Blocks;

import GameWorldAPI.GameWorld.GameWorld;
import System.BlockStructure.Functionality.CavityFunctionality;

/**
 * A class for if blocks. These are cavity blocks which have a
 * cavity functionality. The cavity can only be ran time.
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

    @Override
    protected Block getNewReturnBlock() {
        if (getSubConnectorAt(0).isConnected()) {
            return getSubConnectorAt(0).getConnectedBlock();
        }
        return getReturnToBlock();
    }

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

    @Override
    public Block clone() {
        return new IfBlock();
    }
}
