package Main.BlockStructure.Connections;

public class Plug extends Connector {


    private Socket socket;

    public Plug(Orientation orientation) {
        super(orientation);
    }

    public Socket getSocket() {
        return socket;
    }





    public boolean isConnected() {
        return socket != null;
    }

    /**
     * Check whether this plug can have the given socket as its socket.
     *
     * @param    socket
     *           The socket to check.
     *
     * @return   True if and only if the given socket is not null.
     */
    public boolean canHaveAsSocket(Socket socket) {
        return socket != null;
    }

    /**
     * Check whether this plug has a proper socket attached to it.
     * @return
     */
    public boolean hasProperSocket() {
        return canHaveAsSocket(socket) && ( socket == null || socket.getPlug() == this);
    }

    public void connect (Socket socket) throws Exception {
        if (!canHaveAsSocket(socket)) {
            throw new Exception();
        }
        if (this.socket.isConnected() && socket.getPlug() != this) {
            throw new Exception();
        }
        if (isConnected()) {
            this.socket.disconnect();
        }
        setSocket(socket);
        socket.connect(this);
    }


    private void setSocket(Socket socket) throws Exception{
        if (!canHaveAsSocket(socket)) {
            throw new Exception();
        }
        this.socket = socket;
    }



    public void disconnect() {
        // TODO: disconnect in plug
    }


}
