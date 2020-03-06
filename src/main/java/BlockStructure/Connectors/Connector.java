package BlockStructure.Connectors;

import BlockStructure.Blocks.Block;

public abstract class Connector<B1 extends Block<?>, B2 extends Block<?>> {

    private final Orientation orientation;
    private final B1 block;

    public Connector(B1 block, Orientation orientation) {
        this.block = block;
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public B1 getBlock() {
        return block;
    }
}
