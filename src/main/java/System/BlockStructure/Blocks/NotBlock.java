package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.NotFunctionality;

/**
 * A class for not blocks, which are operational blocks.
 *
 * @author Alpha-team
 */
public class NotBlock extends OperationalBlock {

    /**
     * Initialise a new not block.
     *
     * @effect Calls super constructor with a new functionality and 1 sub connector.
     */
    public NotBlock() {
        super(new NotFunctionality(), 1);
    }

    /**
     * Clone this not block.
     *
     * @return A new not block with a copy of the functionality and which is not
     *         connected to any block.
     */
    @Override
    public Block clone() {
        return new NotBlock();
    }
}