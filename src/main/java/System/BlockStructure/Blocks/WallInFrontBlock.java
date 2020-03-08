package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.WallInFrontFunctionality;

public class WallInFrontBlock extends ConditionalBlock {
    public WallInFrontBlock(int id) {
        super(id, new WallInFrontFunctionality());
    }
}
