package System.BlockStructure.Blocks.Factory;


import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Blocks.TurnRightBlock;
import System.BlockStructure.Functionality.TurnRightFunctionality;

public class TurnRightBlockFactory extends BlockFactory{


    @Override
    public TurnRightBlock getNewBlock() {
        return new FunctionalBlock(getID(), functionality);
    }
}
