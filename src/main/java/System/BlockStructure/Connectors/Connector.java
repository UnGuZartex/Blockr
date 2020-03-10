package System.BlockStructure.Connectors;

import System.BlockStructure.Blocks.Block;

public abstract class Connector {

    /**
     * Variable referring to the connector to which this
     * connector is connected.
     */
    protected Connector connectedConnector;
    /**
     * Variable referring to the orientation of this connector.
     */
    private final Orientation orientation;
    /**
     * Variable referring block of this connector.
     */
    private final Block block;
    /**
     * Variable referring to the type of this connector.
     */
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
