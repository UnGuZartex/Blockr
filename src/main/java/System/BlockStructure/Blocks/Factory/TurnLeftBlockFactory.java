package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Functionality.TurnLeftFunctionality;

public class TurnLeftBlockFactory extends BlockFactory {

    private TurnLeftFunctionality functionality
            = new TurnLeftFunctionality();

    @Override
    protected Block getNewBlock() {
        return new FunctionalBlock(getID(), functionality);
    }
}
