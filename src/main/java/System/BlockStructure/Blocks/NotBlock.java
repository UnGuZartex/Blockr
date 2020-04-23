package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.NotFunctionality;

/**
 * A class for not blocks, which are operational blocks.
 *
 * @author Alpha-team
 */
public class NotBlock extends OperationalBlock {

    /**
     * Initialise a new not block with given functionality.
     *
     * @param functionality The not functionality for this block.
     *
     * @effect Calls super constructor with given functionality and one
     *         one sub connector.
     */
    public NotBlock(NotFunctionality functionality) {
        super(functionality, 1);
    }

    /**
     * Clone this not block.
     *
     * @return A new not block with a copy of the functionality and which is not
     *         connected to any block.
     */
    @Override
    public Block clone() {
        return new NotBlock(new NotFunctionality(functionality.getGameWorld()));
    }
}
