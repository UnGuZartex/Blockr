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
     * @param functionality The functionality for this block.
     */
    public WhileBlock(CavityFunctionality functionality) {
        super(functionality);
    }

    /**
     * TODO documentation
     *
     * @return
     */
    @Override
    public Block getNextIfNone() {
        if (hasAlreadyRan()) {
            return super.getNextIfNone();
        }
        return this;
    }
}
