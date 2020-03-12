package System.BlockStructure.Blocks.Factory;


import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Functionality.TurnLeftFunctionality;

public class TurnLeftBlockFactory extends BlockFactory {

    @Override
    public FunctionalBlock CreateBlock() {
        return new FunctionalBlock(new TurnLeftFunctionality());
    }
}
