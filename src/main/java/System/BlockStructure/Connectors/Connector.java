package System.BlockStructure.Connectors;

import System.BlockStructure.Blocks.Block;

/**
 * An abstract class for connectors. These are the objects
 * which can connect blocks to each other.
 *
 * @invar A connector must have a proper connector at all times.
 *        | hasProperConnector()
 *        
 * @author Alpha-team
 */
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

    /**
     * Initialise a new connector with given block, orientation and type.
     *
     * @param block The block of which this connector is.
     * @param orientation The orientation of this connector.
     * @param type The type of this connector.
     *
     * @post The block of this connector equals the given block.
     * @post The orientation of this connector equals the given orientation.
     * @post The type of this connector equals the given type.
     */
    public Connector(Block block, Orientation orientation, Type type) {
        this.block = block;
        this.orientation = orientation;
        this.type = type;
    }

    /**
     * Get the connected connector of this connector.
     *
     * @return The connected connector of this connector.
     */
    public Connector getConnectedConnector() {
        return connectedConnector;
    }

    /**
     * Get the orientation of this connector.
     *
     * @return The orientation of this connector.
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Get the block of this connector.
     *
     * @return the block of this connector.
     */
    public Block getBlock() {
        return block;
    }

    /**
     * Get the type of this connector.
     *
     * @return The type of this connector.
     */
    public Type getType() {
        return type;
    }

    /**
     * Check whether or not this connector is connected.
     *
     * @return True if and only if the connected connector is effective.
     */
    public boolean isConnected() {
        return connectedConnector != null;
    }

    /**
     * Get the block of the connected connector of this connector.
     *
     * @return The block of the connector connected to this connector. If
     *         this connector is not connected, then null is returned.
     */
    public Block getConnectedBlock() {
        if (isConnected()) {
            return connectedConnector.getBlock();
        } else {
            return null;
        }
    }

    /**
     * Checks if this connector has a proper connected connector.
     *
     * @return True if and only if this connector is not connected or the connected
     *         connector of this connector equals this connector.
     */
    public boolean hasProperConnector() {
        return connectedConnector == null || connectedConnector.getConnectedConnector() == this;
    }

    /**
     * Disconnect this connector from it's connector.
     *
     * @throws IllegalStateException
     *         If this connector is not connected to a connector.
     *
     * @post This connector is not connected anymore.
     */
    protected abstract void disconnect() throws IllegalStateException;
}
