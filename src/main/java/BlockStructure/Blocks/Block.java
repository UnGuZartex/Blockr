package BlockStructure.Blocks;

import BlockStructure.Connectors.Orientation;
import BlockStructure.Connectors.Plug;
import BlockStructure.Functionality.Functionality;

public abstract class Block {

    private final int id;
    private final Plug plug;
    private final Functionality functionality;

    protected Block(int id, Orientation plugOrientation, Functionality functionality) {
        this.id = id;
        this.plug = new Plug(this, plugOrientation);
        this.functionality = functionality;
    }

    public Functionality getFunctionality() {
        return functionality;
    }

    public Plug getPlug() {
        return plug;
    }

    public boolean hasNext() {
        return getPlug().isConnected();
    }

    public Block getNext() {
        return plug.getSocket().getBlock();
    }
}
