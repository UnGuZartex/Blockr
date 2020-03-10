package System.BlockStructure.Blocks.Factory;


import System.BlockStructure.Blocks.TurnLeftBlock;
import System.BlockStructure.Functionality.TurnLeftFunctionality;

public class TurnLeftBlockFactory extends BlockFactory {

    @Override
    public TurnLeftBlock getNewBlock() {
        return new TurnLeftBlock();
    }
}
