package BlockStructure.Factory;

import BlockStructure.Blocks.*;
import BlockStructure.Functionality.Functionality;
import jdk.jshell.spi.ExecutionControl;

public abstract class BlockFactory {

    private static int id = -1;

    protected int getID() {
        return id;
    }

    public Block CreateBlock() {
        id++;
        return getNewBlock();
    }

    public abstract Block getNewBlock();
}
