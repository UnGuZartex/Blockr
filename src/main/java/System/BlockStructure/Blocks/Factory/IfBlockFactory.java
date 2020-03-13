package System.BlockStructure.Blocks.Factory;


import System.BlockStructure.Blocks.IfBlock;
import System.BlockStructure.Functionality.CavityFunctionality;

/**
 * A factory to create an If-block
 *
 * @author Alpha-team
 */
public class IfBlockFactory extends BlockFactory {

    /**
     * Create an if-block
     * @return a new if-block
     */
    @Override
    public IfBlock createBlock() {
        return new IfBlock(new CavityFunctionality());
    }
}
