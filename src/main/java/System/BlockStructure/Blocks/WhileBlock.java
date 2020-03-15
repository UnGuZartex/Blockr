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
     *
     * @effect Calls super constructor with given functionality.
     */
    public WhileBlock(CavityFunctionality functionality) {
        super(functionality);
    }

    /**
     * Get the next block to execute, this depends on the evaluation of the condition of this block.
     * If the condition is true, the next block is the first in the cavity, otherwise, the next block
     * is the first block under the while.
     *
     * @return The next block to execute
     */
    @Override
    public Block getNext() {
        if (getFunctionality().getEvaluation()) {
            setAlreadyRan(false);
            return getCavitySubConnector().getConnectedBlock();
        }
        else if(getCavitySubConnector().isConnected()) {
            getCavitySubConnector().getConnectedBlock().reset();
        }
        setAlreadyRan(true);
        return super.getNext();
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
