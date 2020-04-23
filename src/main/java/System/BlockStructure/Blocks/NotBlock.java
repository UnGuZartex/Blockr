package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.NotFunctionality;

public class NotBlock extends OperationalBlock {

    public <B extends OperationalBlock> NotBlock(NotFunctionality functionality) {
        super(functionality, 1);
    }

    @Override
    public Block clone() {
        return new NotBlock(new NotFunctionality());
    }
}
