package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.NotFunctionality;

/**
 * A class for not blocks, which are operational blocks.
 *
 * @author Alpha-team
 */
public class NotBlock extends OperationalBlock {

    public <B extends OperationalBlock> NotBlock() {
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
