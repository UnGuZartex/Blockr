package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.StatementBlock;
import System.BlockStructure.Functionality.WallInFrontFunctionality;

/**
 * A factory to create wall in front blocks.
 *
 * @author Alpha-team
 */
public class WallInFrontBlockFactory extends BlockFactory {

    /**
     * Create a new wall in front block.
     *
     * @return A new statement block with wall in front functionality.
     */
    @Override
    public StatementBlock createBlock() {
        return new StatementBlock(new WallInFrontFunctionality());
    }
}
