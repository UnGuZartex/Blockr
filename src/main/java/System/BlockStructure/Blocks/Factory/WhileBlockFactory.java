package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.WhileBlock;
import System.BlockStructure.Functionality.CavityFunctionality;

/**
 * A factory for while blocks.
 *
 * @author Alpha-team
 */
public class WhileBlockFactory extends BlockFactory {

    /**
     * Create a new while block.
     *
     * @return A new while block with cavity functionality.
     */
    @Override
    public WhileBlock createBlock() {
        return new WhileBlock(new CavityFunctionality());
    }
}
