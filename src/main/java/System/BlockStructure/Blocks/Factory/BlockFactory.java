package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.*;

public abstract class BlockFactory {

    public Block CreateBlock() {
        return getNewBlock();
    }

    protected abstract Block getNewBlock();
}
