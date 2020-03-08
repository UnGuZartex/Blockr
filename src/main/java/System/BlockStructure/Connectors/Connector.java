package System.BlockStructure.Connectors;

import System.BlockStructure.Blocks.Block;

public abstract class Connector {

    protected Connector connectedConnector;
    private final Orientation orientation;
    private final Block block;


    private final Type type;

    public Connector(Block block, Orientation orientation, Type type) {
        this.block = block;
        this.orientation = orientation;
        this.type = type;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Connector getConnectedConnector() {
        return connectedConnector;
    }


    public Type getType() {
        return type;
    }

    public boolean isConnected() {
        return connectedConnector != null;
    }

    public Block getBlock() {
        return block;
    }

    public Block getConnectedBlock() {
        return connectedConnector.getBlock();
    }

    protected abstract void disconnect() throws IllegalStateException;
}
