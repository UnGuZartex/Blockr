package BlockStructure.Blocks;

import BlockStructure.Connectors.*;
import BlockStructure.Functionality.Functionality;


public abstract class ConditionalBlock extends Block {

    private final Plug plug;

    public ConditionalBlock(Functionality functionality) {
        super(functionality); // TODO
        this.plug = new Plug(this, Orientation.FACING_LEFT);
    }
}
