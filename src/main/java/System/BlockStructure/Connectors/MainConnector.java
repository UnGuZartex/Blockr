package System.BlockStructure.Connectors;

import System.BlockStructure.Blocks.Block;

public class MainConnector extends Connector {

    public MainConnector(Block block, Orientation orientation, Type type) {
        super(block, orientation, type);
    }

    public void connect (SubConnector subConnector) throws IllegalStateException, IllegalArgumentException  {

        if (this.isConnected()) {
            throw new IllegalStateException("This plug is already connected to another socket!");
        }

        if (subConnector.isConnected()) {
            throw new IllegalArgumentException("The given socket is already connected to another plug!");
        }

        if (!canHaveAsSocket(subConnector)) {
            throw new IllegalArgumentException("The given socket is not compatible with this plug!");
        }

        connectedConnector = subConnector;
        subConnector.connect(this);
    }

    @Override
    public void disconnect() throws IllegalStateException {

        if (!this.isConnected()) {
            throw new IllegalStateException("This plug is not connected to a socket!");
        }

        Connector tempSocket = connectedConnector;
        connectedConnector = null;
        tempSocket.disconnect();
    }

    private boolean canHaveAsSocket(SubConnector subConnector) {
        return subConnector != null && getOrientation().isOpposite(subConnector.getOrientation()) && getType().canConnectWith(subConnector.getType());
    }

}
