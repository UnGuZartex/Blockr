package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Functionality.TurnRightFunctionality;

public class TurnRightBlockFactory extends BlockFactory{

    private TurnRightFunctionality functionality
            = new TurnRightFunctionality();

    @Override
    protected Block getNewBlock() {
        return new FunctionalBlock(getID(), functionality);
    }
}
