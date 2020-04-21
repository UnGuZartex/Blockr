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
     * Initialise a new if block  with given cavity functionality.
     *
     *
     * @effect Calls super constructor with given functionality.
     */
    public IfBlock(CavityFunctionality functionality) {
        super(functionality);
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
     * @return A new if block with a copy of the current functionality and which is not
     *         connected to any block.
     */
    @Override
    public Block clone() {
        return new IfBlock((CavityFunctionality) functionality.copy());
    }
}
