package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.*;
import System.BlockStructure.Functionality.BlockFunctionality;

public abstract class ConditionalBlock extends Block {

    private final Plug leftPlug;

    public ConditionalBlock(int id, BlockFunctionality functionality) {
        super(id, functionality);
        leftPlug = new Plug(this, Orientation.FACING_LEFT);
    }

    public Plug getLeftPlug() {
        return leftPlug;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Block getNext() {
        return null;
    }

    @Override
    public boolean canBeStarter() {
        return false;
    }
}
