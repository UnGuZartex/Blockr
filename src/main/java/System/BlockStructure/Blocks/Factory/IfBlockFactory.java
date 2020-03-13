package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.IfBlock;
import System.BlockStructure.Functionality.CavityFunctionality;

/**
 * A factory to create an If-blocks.
 *
 * @author Alpha-team
 */
public class IfBlockFactory extends BlockFactory {

    /**
     * Create an if-block.
     *
     * @return a new If block with cavity functionality.
     */
    @Override
    public IfBlock createBlock() {
        return new IfBlock(new CavityFunctionality());
    }
}
