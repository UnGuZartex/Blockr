package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Functionality.TurnRightFunctionality;

/**
 * A factory to create turn right blocks.
 *
 * @author Alpha-team
 */
public class TurnRightBlockFactory extends BlockFactory{

    /**
     * Create a new turn right block.
     *
     * @return A new functional block with turn right functionality.
     */
    @Override
    public FunctionalBlock createBlock() {
        return new FunctionalBlock(new TurnRightFunctionality());
    }
}
