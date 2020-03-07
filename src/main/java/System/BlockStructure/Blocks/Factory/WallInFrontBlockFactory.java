package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.ConditionalBlock;
import System.BlockStructure.Functionality.WallInFrontFunctionality;

public class WallInFrontBlockFactory extends BlockFactory {
    @Override
    protected ConditionalBlock getNewBlock() {
        return new WallInFrontFunctionality(getID()).getBlock();
    }
}
