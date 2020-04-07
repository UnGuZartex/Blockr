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


    @Override
    protected Block getNewReturnBlock() {
        return this;
    }

    @Override
    public Block clone() {
        return new WhileBlock(new CavityFunctionality(functionality.getGameWorld()));
    }
}
