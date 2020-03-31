package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.NotFunctionality;

public class NotBlock extends OperationalBlock {

    public <B extends OperationalBlock> NotBlock() {
        super(new NotFunctionality(), 1);
    }

    @Override
    public Block clone() {
        return new NotBlock();
    }
}
