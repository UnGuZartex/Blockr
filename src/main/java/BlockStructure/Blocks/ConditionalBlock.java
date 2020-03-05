package BlockStructure.Blocks;

import BlockStructure.Connectors.*;
import BlockStructure.Functionality.ConditionalFunctionality;

public class ConditionalBlock extends Block {

    public ConditionalBlock(int id, ConditionalFunctionality functionality) {
        super(id, Orientation.FACING_LEFT, functionality);
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
