package System.BlockStructure.Connectors;

import System.BlockStructure.Blocks.Block;

/**
 * A class for main connectors. These are the connectors
 * which will connect to sub connectors.
 *
 * @author Alpha-team
 */
public class MainConnector extends Connector {

    /**
     * Initialise a new main connector with given block, orientation and type.
     *
     * @param block The block of this main connector.
     * @param orientation The orientation of this main connector.
     * @param type The type of this main connector.
     */
    public MainConnector(Block block, Orientation orientation, Type type) {
        super(block, orientation, type);
    }

    /**
     * Connect this main connector to the given sub connector. The given
     * sub connector will also be connected to this main connector.
     *
     * @param subConnector The sub connector to connect to.
     *
     * @throws IllegalStateException
     *         If this main connector is already connected.
     * @throws IllegalArgumentException
     *         If the given connector is already connected.
     * @throws IllegalArgumentException
     *         If the given connector can not be a connector.
     */
    public void connect(SubConnector subConnector) throws IllegalStateException, IllegalArgumentException  {

        if (this.isConnected()) {
            throw new IllegalStateException("This main connector is already connected to another sub connector!");
        }

        if (!canHaveAsSubConnector(subConnector)) {
            throw new IllegalArgumentException("The given sub connector is not compatible with this main connector!");
        }

        if (subConnector.isConnected()) {
            throw new IllegalArgumentException("The given sub connector is already connected to another main connector!");
        }

        connectedConnector = subConnector;
        subConnector.connect(this);
    }

    /**
     * Disconnect this main connector from it"s connected connector. The
     * connected connector will also disconnect from this connector.
     *
     * @throws IllegalStateException
     *         If the this connector is not connected.
     */
    @Override
    public void disconnect() throws IllegalStateException {
        if (!this.isConnected()) {
            throw new IllegalStateException("This main connector is not connected!");
        }

        Connector tempSocket = connectedConnector;
        connectedConnector = null;
        tempSocket.disconnect();
    }

    /**
     * Check if the given sub connector can be connected to this main connector.
     *
     * @param subConnector The subconnector to check.
     *
     * @return True if and only if the given sub connector is effective, has an
     *         opposite orientation to the orientation of this main connector and
     *         has a connectable type to the type of this main connector.
     */
    public boolean canHaveAsSubConnector(SubConnector subConnector) {
        return subConnector != null && getOrientation().isOpposite(subConnector.getOrientation()) && getType().canConnectWith(subConnector.getType());
    }
}
