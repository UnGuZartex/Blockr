package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.WhileBlock;
import System.BlockStructure.Functionality.CavityFunctionality;

public class WhileBlockFactory extends BlockFactory {

    @Override
    public WhileBlock createBlock() {
        return new WhileBlock(new CavityFunctionality());
    }
}
