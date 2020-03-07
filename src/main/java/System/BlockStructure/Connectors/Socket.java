package System.BlockStructure.Connectors;

import System.BlockStructure.Blocks.Block;

public class Socket<B1 extends Block<?>, B2 extends Block<?>> extends Connector<B1, B2> {

    public Socket(B1 block, Orientation orientation) {
        super(block, orientation);
    }

    protected void connect (Plug<B2, B1> plug) throws IllegalStateException, IllegalArgumentException {

        if (this.isConnected()) {
            throw new IllegalStateException("This socket is already connected to another plug!");
        }

        if (plug.isConnected() && plug.getConnectedConnector() != this) {
            throw new IllegalArgumentException("The given plug is already connected to another socket!");
        }

        connectedConnector = plug;
    }

    @Override
    protected void disconnect() throws IllegalStateException {

        if (connectedConnector.isConnected()) {
            throw new IllegalStateException("This socket is not connected to a plug!");
        }

        connectedConnector = null;
    }
}
