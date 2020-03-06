package BlockStructure.Blocks;

import BlockStructure.Connectors.*;
import BlockStructure.Functionality.ConditionalFunctionality;

public class ConditionalBlock<F extends ConditionalFunctionality> extends Block<F> {

    private final Plug<ConditionalBlock<?>, Block<?>> leftPlug;

    public ConditionalBlock(int id, F functionality) {
        super(id, functionality);
        leftPlug = new Plug<>(this, Orientation.FACING_LEFT);
    }

    public Plug<ConditionalBlock<?>, Block<?>> getLeftPlug() {
        return leftPlug;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public ConditionalBlock<?> getNext() {
        return null;
    }
}
