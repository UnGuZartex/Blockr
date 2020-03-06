package BlockStructure.Connectors;

import BlockStructure.Blocks.Block;

public abstract class Connector<B1 extends Block<?>, B2 extends Block<?>> {

    protected Connector<B2, B1> connectedConnector;
    private final Orientation orientation;
    private final B1 block;

    public Connector(B1 block, Orientation orientation) {
        this.block = block;
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Connector<B2, B1> getConnectedConnector() {
        return connectedConnector;
    }

    public boolean isConnected() {
        return connectedConnector != null;
    }

    public B1 getBlock() {
        return block;
    }

    protected abstract void disconnect() throws Exception;
}
