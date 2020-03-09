package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.NotFunctionality;

public class NotBlock extends OperationalBlock {
    public <B extends OperationalBlock> NotBlock(int id) {
        super(new NotFunctionality(), 1);
    }
}
