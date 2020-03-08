package System.BlockStructure.Connectors;

import System.BlockStructure.Blocks.Block;

public class SubConnector extends Connector {

    public SubConnector(Block block, Orientation orientation, Type type) {
        super(block, orientation, type);
    }

    protected void connect (MainConnector mainConnector) throws IllegalStateException, IllegalArgumentException {

        if (this.isConnected()) {
            throw new IllegalStateException("This socket is already connected to another plug!");
        }

        if (mainConnector.isConnected() && mainConnector.getConnectedConnector() != this) {
            throw new IllegalArgumentException("The given plug is already connected to another socket!");
        }

        connectedConnector = mainConnector;
    }

    @Override
    protected void disconnect() throws IllegalStateException {

        if (connectedConnector.isConnected()) {
            throw new IllegalStateException("This socket is not connected to a plug!");
        }

        connectedConnector = null;
    }
}
