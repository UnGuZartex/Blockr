package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.MoveForwardBlock;

public class MoveForwardBlockFactory extends BlockFactory {

    @Override
    public MoveForwardBlock CreateBlock() {
        return new MoveForwardBlock();
    }
}
