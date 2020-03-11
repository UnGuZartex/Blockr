package System.BlockStructure.Connectors;

import System.BlockStructure.Blocks.Block;

public class MainConnector extends Connector {

    public MainConnector(Block block, Orientation orientation, Type type) {
        super(block, orientation, type);
    }

    public void connect(SubConnector subConnector) throws IllegalStateException, IllegalArgumentException  {

        if (this.isConnected()) {
            throw new IllegalStateException("This main connector is already connected to another sub connector!");
        }

        if (!canHaveAsSocket(subConnector)) {
            throw new IllegalArgumentException("The given sub connector is not compatible with this main connector!");
        }

        if (subConnector.isConnected()) {
            throw new IllegalArgumentException("The given sub connector is already connected to another main connector!");
        }

        connectedConnector = subConnector;
        subConnector.connect(this);
    }

    @Override
    public void disconnect() throws IllegalStateException {
        if (!this.isConnected()) {
            throw new IllegalStateException("This main connecter is not connected!");
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
    public boolean canHaveAsSocket(SubConnector subConnector) { // TODO rename to subconnector
        return subConnector != null && getOrientation().isOpposite(subConnector.getOrientation()) && getType().canConnectWith(subConnector.getType());
    }

}
