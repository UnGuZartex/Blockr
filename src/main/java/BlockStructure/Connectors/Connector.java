package BlockStructure.Connectors;

import BlockStructure.Blocks.Block;

public abstract class Connector<B extends Block> {


    private final Orientation orientation;
    private final B block;


    public Connector(B block, Orientation orientation) {
        this.block = block;
        this.orientation = orientation;
    }


    public Orientation getOrientation() {
        return orientation;
    }


    public B getBlock() {
        return block;
    }
}
