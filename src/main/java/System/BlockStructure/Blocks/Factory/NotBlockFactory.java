package System.BlockStructure.Blocks.Factory;


import System.BlockStructure.Blocks.OperationalBlock;
import System.BlockStructure.Functionality.NotFunctionality;

public class NotBlockFactory extends BlockFactory{
    @Override
    public OperationalBlock createBlock() {
        return new OperationalBlock(new NotFunctionality(), 1);
    }
}
