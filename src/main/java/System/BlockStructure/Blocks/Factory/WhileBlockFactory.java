package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.WhileBlock;

public class WhileBlockFactory extends BlockFactory {
    @Override
    public WhileBlock getNewBlock() {
        return new WhileBlock();
    }
}
