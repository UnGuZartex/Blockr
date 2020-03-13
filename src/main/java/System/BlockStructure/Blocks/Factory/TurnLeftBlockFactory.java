package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Functionality.TurnLeftFunctionality;

/**
 * Factory to create Turn Left blocks
 *
 * @author Alpha-team
 */
public class TurnLeftBlockFactory extends BlockFactory {

    /**
     * Create a new turn left block.
     *
     * @return A new functional block with the turn left functionality.
     */
    @Override
    public FunctionalBlock createBlock() {
        return new FunctionalBlock(new TurnLeftFunctionality());
    }
}
