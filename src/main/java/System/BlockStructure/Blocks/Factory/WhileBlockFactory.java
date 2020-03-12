package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.WhileBlock;
import System.BlockStructure.Functionality.WhileFunctionality;

public class WhileBlockFactory extends BlockFactory {
    @Override
    public WhileBlock CreateBlock() {
        return new WhileBlock(new WhileFunctionality());
    }
}
