package System.BlockStructure.Blocks;


import System.BlockStructure.Functionality.PredicateFunctionality;

/**
 * A class for statement blocks. These are conditional blocks which
 * declare some kind of statement.
 *
 * @author Alpha-team
 */
public class PredicateBlock extends ConditionalBlock {

    /**
     * Initialise a new statement block with given functionality.
     *
     * @param functionality The functionality for this block
     *
     * @effect Calls super constructor with given functionality.
     */
    public PredicateBlock(PredicateFunctionality functionality) {
        super(functionality);
    }

    /**
     * Get a clone of this block.
     *
     * @return A new statement block with the functionality of this block which
     *         is not connected yet.
     */
    @Override
    public Block clone() {
        return new PredicateBlock((PredicateFunctionality) functionality);
    }
}
