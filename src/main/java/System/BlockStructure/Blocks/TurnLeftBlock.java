package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.TurnLeftFunctionality;

public class TurnLeftBlock extends FunctionalBlock {
    public TurnLeftBlock(int id) {
        super(id, new TurnLeftFunctionality());
    }
}
