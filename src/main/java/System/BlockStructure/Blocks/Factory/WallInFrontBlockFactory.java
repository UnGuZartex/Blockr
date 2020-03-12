package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.StatementBlock;
import System.BlockStructure.Functionality.WallInFrontFunctionality;

public class WallInFrontBlockFactory extends BlockFactory {
    @Override
    public StatementBlock createBlock() {
        return new StatementBlock(new WallInFrontFunctionality());
    }
}
