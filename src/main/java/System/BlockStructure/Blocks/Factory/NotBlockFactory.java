package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.OperationalBlock;
import System.BlockStructure.Functionality.NotFunctionality;

/**
 * A factory to create not blocks.
 *
 * @author Alpha-team
 */
public class NotBlockFactory extends BlockFactory {

    /**
     * Create a new not block.
     *
     * @return A new operational block with the not functionality and one sub connector.
     */
    @Override
    public OperationalBlock createBlock() {
        return new OperationalBlock(new NotFunctionality(), 1);
    }
}
