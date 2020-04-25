package System.BlockStructure.Blocks;


import System.BlockStructure.Functionality.PredicateFunctionality;

/**
 * A class for statement blocks. These are conditional blocks which
 * declare some kind of statement.
 *
 * @author Alpha-team
 */
public class StatementBlock extends ConditionalBlock {

    /**
     * Initialise a new statement block with given functionality.
     *
     * @param functionality The functionality for this block
     *
     * @effect Calls super constructor with given functionality.
     */
    public StatementBlock(PredicateFunctionality functionality) {
        super(functionality);
    }

    /**
     * Get a clone of this block.
     *
     * @return A new statement block with a copy of the functionality and which
     *         is not connected yet.
     */
    @Override
    public Block clone() {
        return new StatementBlock((PredicateFunctionality) functionality);
    }
}
