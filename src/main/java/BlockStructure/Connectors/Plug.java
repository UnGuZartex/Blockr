package BlockStructure.Connectors;

import BlockStructure.Blocks.Block;

public class Plug extends Connector {

    private Socket socket;

    public Plug(Block block, Orientation orientation) {
        super(block, orientation);
    }


    public Socket getSocket() {
        return socket;
    }


    /**
     * Check whether this plug is connected to any socket.
     *
     * @return True if and only if this plug has an existing socket.
     */
    public boolean isConnected() {
        return socket != null;
    }

    /**
     * Checks whether this plug has a proper socket.
     *
     * @return True if and only the socket of this plug is not existing
     *         or the plug connected to the socket of this plug equals
     *         this plug.
     */
    public boolean hasProperSocket() {
        return socket == null || socket.getPlug() == this;
    }

    /**
     * Checks whether this plug can have the given socket as its socket.
     *
     * @param    socket
     *           The socket to check.
     *
     * @return   True if and only if the given socket is effective.
     */
    public boolean canHaveAsSocket(Socket socket) {
        return socket != null;
    }

    public void connect (Socket socket) throws Exception {
        // TODO deftige errors
        if (this.isConnected()) {
            throw new Exception();
        }
        if (socket.isConnected()) {
            throw new Exception();
        }
        if (!canHaveAsSocket(socket)) {
            throw new Exception();
        }
        this.socket = socket;
        socket.connect(this);
    }

    public void disconnect() throws Exception {
        if (!this.isConnected()) { // Is not connected, thus can't disconnect
            throw new Exception();
        }
        Socket tempSocket = socket;
        socket = null;
        tempSocket.disconnect();
    }
}
