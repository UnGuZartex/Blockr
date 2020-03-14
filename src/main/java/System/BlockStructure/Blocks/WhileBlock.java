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
     * Returns the next block if any other block didn't have a next block to run.
     *
     * @return if this block has already ran all of its connectors then return the closest
     * cavity. Otherwise return itself for evaluation.
     */
    @Override
    public Block getNextIfNone() {
        if (hasAlreadyRan()) {
            return super.getNextIfNone();
        }
        return this;
    }
}
