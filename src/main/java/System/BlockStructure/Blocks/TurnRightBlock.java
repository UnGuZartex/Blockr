package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.TurnRightFunctionality;

public class TurnRightBlock extends FunctionalBlock {
    public TurnRightBlock(int id) {
        super(id, new TurnRightFunctionality());
    }
}
