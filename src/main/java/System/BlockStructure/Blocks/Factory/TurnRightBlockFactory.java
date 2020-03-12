package System.BlockStructure.Blocks.Factory;


import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Functionality.TurnRightFunctionality;

public class TurnRightBlockFactory extends BlockFactory{


    @Override
    public FunctionalBlock CreateBlock() {
        return new FunctionalBlock(new TurnRightFunctionality());
    }
}
