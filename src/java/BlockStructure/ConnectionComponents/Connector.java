package BlockStructure.ConnectionComponents;

import BlockStructure.Block;

public abstract class Connector {


    private final Orientation orientation;
    private Block block; // TODO bidirect nog aanleggen (connector is controller)


    public Connector(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * TODO
     * @return
     */
    public Orientation getOrientation() {
        return orientation;
    }


    public Block getBlock() {
        return block;
    }
}
