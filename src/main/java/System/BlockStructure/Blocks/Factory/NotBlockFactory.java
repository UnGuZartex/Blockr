package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.ConditionalBlock;
import System.BlockStructure.Functionality.NotFunctionality;

public class NotBlockFactory extends BlockFactory{
    @Override
    public ConditionalBlock getNewBlock() {
        return new NotFunctionality(getID()).getBlock();
    }
}
