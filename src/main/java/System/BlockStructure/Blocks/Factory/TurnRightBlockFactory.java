package System.BlockStructure.Blocks.Factory;


import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Functionality.TurnRightFunctionality;

public class TurnRightBlockFactory extends BlockFactory{

    private TurnRightFunctionality functionality
            = new TurnRightFunctionality();

    @Override
    protected FunctionalBlock getNewBlock() {
        return new FunctionalBlock(getID(), functionality);
    }
}
