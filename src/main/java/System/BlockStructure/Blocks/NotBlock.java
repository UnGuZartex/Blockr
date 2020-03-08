package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.NotFunctionality;

public class NotBlock extends OperationalBlock {
    public <B extends OperationalBlock> NotBlock(int id) {
        super(id, new NotFunctionality(), 1);
    }
}
