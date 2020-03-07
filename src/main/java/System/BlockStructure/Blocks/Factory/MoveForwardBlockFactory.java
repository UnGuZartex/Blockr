package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Functionality.MoveForwardFunctionality;

public class MoveForwardBlockFactory extends BlockFactory {

    private MoveForwardFunctionality functionality
            = new MoveForwardFunctionality();

    @Override
    public FunctionalBlock getNewBlock() {
        return new FunctionalBlock(getID(), functionality);
    }
}
