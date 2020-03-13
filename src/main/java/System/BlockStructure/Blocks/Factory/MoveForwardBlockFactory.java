package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Functionality.MoveForwardFunctionality;

/**
 * A factory for move forward blocks.
 *
 * @author Alpha-team
 */
public class MoveForwardBlockFactory extends BlockFactory {

    /**
     * Create a new move forward block.
     *
     * @return A new functional block with move forward functionality.
     */
    @Override
    public FunctionalBlock createBlock() {
        return new FunctionalBlock(new MoveForwardFunctionality());
    }
}
