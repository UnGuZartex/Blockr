package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.ConditionalBlock;
import System.BlockStructure.Blocks.WallInFrontBlock;

public class WallInFrontBlockFactory extends BlockFactory {
    @Override
    public ConditionalBlock getNewBlock() {
        return new WallInFrontBlock();
    }
}
