package BlockStructure.Connectors;

import BlockStructure.Blocks.Block;

public class Plug<B1 extends Block<?>, B2 extends Block<?>> extends Connector<B1, B2> {

    public Plug(B1 block, Orientation orientation) {
        super(block, orientation);
    }

    public boolean hasProperSocket() {
        return connectedConnector == null || connectedConnector.getConnectedConnector() == this;
    }

    public boolean canHaveAsSocket(Socket<B2, B1> socket) {
        return socket != null && getOrientation().isOpposite(socket.getOrientation());
    }

    public void connect (Socket<B2, B1> socket) throws Exception {

        // TODO specifieke errors
        if (this.isConnected()) {
            throw new Exception();
        }
        if (socket.isConnected()) {
            throw new Exception();
        }
        if (!canHaveAsSocket(socket)) {
            throw new Exception();
        }

        connectedConnector = socket;
        socket.connect(this);
    }

    @Override
    public void disconnect() throws Exception {
        if (!this.isConnected()) { // Is not connected, thus can't disconnect
            throw new Exception();
        }

        Connector<B2, B1> tempSocket = connectedConnector;
        connectedConnector = null;
        tempSocket.disconnect();
    }
}
