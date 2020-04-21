package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.CavityFunctionality;

/**
 * A class for while blocks. These are cavity blocks which have a
 * cavity functionality. The cavity can be repeated multiple times.
 *
 * @author Alpha-team
 */
public class WhileBlock extends CavityBlock {

    /**
     * Initialise a new while block with given cavity functionality.
     *
     * @effect Calls super constructor with given functionality.
     */
    public WhileBlock(CavityFunctionality functionality) {
        super(functionality);
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
        return new WhileBlock((CavityFunctionality) functionality.copy());
    }
}
