package System.BlockStructure.Connectors;

import System.BlockStructure.Blocks.Block;

public class Socket<B1 extends Block<?>, B2 extends Block<?>> extends Connector<B1, B2> {

    public Socket(B1 block, Orientation orientation) {
        super(block, orientation);
    }

    protected void connect (Plug<B2, B1> plug) throws Exception {

        // TODO specifieke errors
        if (this.isConnected()) {
            throw new Exception();
        }

        if (!plug.isConnected() && plug.getConnectedConnector() != this) {
            throw new Exception();
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
