package System.BlockStructure.Blocks;


import System.BlockStructure.Functionality.ConditionalBlockFunctionality;

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
    public StatementBlock(ConditionalBlockFunctionality<StatementBlock> functionality) {
        super(functionality);
    }
}
