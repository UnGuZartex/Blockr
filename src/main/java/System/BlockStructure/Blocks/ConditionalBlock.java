package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.Plug;
import System.BlockStructure.Functionality.ConditionalBlockFunctionality;

public abstract class ConditionalBlock extends Block {

    private final Plug leftPlug;

    protected <B extends ConditionalBlock> ConditionalBlock(int id, ConditionalBlockFunctionality<B> functionality) {
        super(id, functionality);
        functionality.setBlock((B) this);
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
