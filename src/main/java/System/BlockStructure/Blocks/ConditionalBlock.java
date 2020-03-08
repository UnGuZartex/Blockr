package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.Plug;
import System.BlockStructure.Functionality.ConditionalBlockFunctionality;

public abstract class ConditionalBlock extends Block {

    private final Plug leftPlug;

    protected ConditionalBlock(int id, ConditionalBlockFunctionality<ConditionalBlock> functionality) {
        super(id, functionality);
        functionality.setBlock(this);
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
