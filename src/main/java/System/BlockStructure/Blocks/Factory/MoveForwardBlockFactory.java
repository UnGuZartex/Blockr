package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.BasicBlock;
import System.BlockStructure.Functionality.MoveForwardFunctionality;

public class MoveForwardBlockFactory extends BlockFactory {

    private MoveForwardFunctionality functionality
            = new MoveForwardFunctionality();

    @Override
    protected Block getNewBlock() {
        return new BasicBlock(getID(), functionality);
    }
}
