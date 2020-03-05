package BlockStructure.Blocks.Factory;

import BlockStructure.Blocks.*;

public abstract class BlockFactory {

    private static int id = -1;

    protected int getID() {
        return id;
    }

    public Block CreateBlock() {
        id++;
        return getNewBlock();
    }

    protected abstract Block getNewBlock();
}
