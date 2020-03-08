package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.MoveForwardFunctionality;

public class MoveForwardBlock extends FunctionalBlock {
    public MoveForwardBlock(int id) {
        super(id, new MoveForwardFunctionality());
    }
}
