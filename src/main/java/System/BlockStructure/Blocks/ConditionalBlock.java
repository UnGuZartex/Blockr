package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.*;
import System.BlockStructure.Functionality.ConditionalFunctionality;
import System.BlockStructure.Functionality.IFunctionality;

public class ConditionalBlock extends Block<ConditionalFunctionality> {

    private final Plug<ConditionalBlock, Block<?>> leftPlug;

    public ConditionalBlock(int id, ConditionalFunctionality functionality) {
        super(id, functionality);
        leftPlug = new Plug<>(this, Orientation.FACING_LEFT);
    }

    public Plug<ConditionalBlock, Block<?>> getLeftPlug() {
        return leftPlug;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public ConditionalBlock getNext() {
        return null;
    }
}
