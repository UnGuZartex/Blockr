package System.BlockStructure.Connectors;

import System.BlockStructure.Blocks.Block;

public class Plug<B1 extends Block<?>, B2 extends Block<?>> extends Connector<B1, B2> {

    public Plug(B1 block, Orientation orientation) {
        super(block, orientation);
    }

    public void connect (Socket<B2, B1> socket) throws IllegalStateException, IllegalArgumentException  {

        if (this.isConnected()) {
            throw new IllegalStateException("This plug is already connected to another socket!");
        }

        if (socket.isConnected()) {
            throw new IllegalArgumentException("The given socket is already connected to another plug!");
        }

        if (!canHaveAsSocket(socket)) {
            throw new IllegalArgumentException("The given socket is not compatible with this plug!");
        }

        connectedConnector = socket;
        socket.connect(this);
    }

    @Override
    public void disconnect() throws IllegalStateException {

        if (!this.isConnected()) {
            throw new IllegalStateException("This plug is not connected to a socket!");
        }

        Connector<B2, B1> tempSocket = connectedConnector;
        connectedConnector = null;
        tempSocket.disconnect();
    }

    private boolean canHaveAsSocket(Socket<B2, B1> socket) {
        return socket != null && getOrientation().isOpposite(socket.getOrientation());
    }
}
