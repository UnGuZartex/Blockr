package System.BlockStructure.Connectors;

import System.BlockStructure.Blocks.Block;

/**
 * A class for sub connectors. This is a type of connector which
 * can be connected to a main connector.
 *
 * @author Alpha-team
 */
public class SubConnector extends Connector {

    /**
     * Initialise a new sub connector with given block, orientation and type.
     *
     * @param block The block to which this connector belongs.
     * @param orientation The orientation of this connector.
     * @param type The type of this connector.
     *
     * @effect calls the super constructor with given block, orientation and type.
     */
    public SubConnector(Block block, Orientation orientation, Type type) {
        super(block, orientation, type);
    }

    /**
     * Connect this sub connector to the given main connector.
     *
     * @param mainConnector The main connector to connect to.
     *
     *
     * @post This sub connector is connected to the given main connector.
     *
     * @throws IllegalStateException
     *         This connector is already connected.
     * @throws IllegalArgumentException
     *         The given connector is not connected.
     * @throws IllegalArgumentException
     *         The given connector is already connected.
     */
    protected void connect (MainConnector mainConnector) throws IllegalStateException, IllegalArgumentException {
        if (this.isConnected()) {
            throw new IllegalStateException("This sub connector is already connected to another main connector!");
        }

        if (!mainConnector.isConnected()) {
            throw new IllegalArgumentException("The given main connector should already be connected!");
        }

        if (mainConnector.getConnectedConnector() != this) {
            throw new IllegalArgumentException("The given main connector is already connected to another sub connector!");
        }

        connectedConnector = mainConnector;
    }

    /**
     * Disconnect this sub connector from its sub connector.
     *
     * @post This sub connector isn't connected to any sub connector anymore.
     *
     * @throws IllegalStateException
     *         When the sub connector initiates the disconnect.
     */
    @Override
    protected void disconnect() throws IllegalStateException {

        if (connectedConnector.isConnected()) {
            throw new IllegalStateException("The sub connector cannot initiate the disconnect!");
        }

        connectedConnector = null;
    }
}
