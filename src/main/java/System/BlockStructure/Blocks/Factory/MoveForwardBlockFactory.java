package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Functionality.MoveForwardFunctionality;

public class MoveForwardBlockFactory extends BlockFactory {

    @Override
    public FunctionalBlock CreateBlock() {
        return new FunctionalBlock(new MoveForwardFunctionality());
    }
}
