package BlockStructure.Connectors;

import BlockStructure.Blocks.Block;

public abstract class Connector {


    private final Orientation orientation;
    private final Block block;


    public Connector(Block block, Orientation orientation) {
        this.block = block;
        this.orientation = orientation;
    }


    public Orientation getOrientation() {
        return orientation;
    }


    public Block getBlock() {
        return block;
    }
}
