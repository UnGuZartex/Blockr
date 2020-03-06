package BlockStructure.Connectors;

import BlockStructure.Blocks.Block;

public class Plug<B1 extends Block, B2 extends Block> extends Connector<B1, B2> {

    private Socket<B2, B1> socket;

    public Plug(B1 block, Orientation orientation) {
        super(block, orientation);
    }


    public Socket<B2, B1> getSocket() {
        return socket;
    }


    public boolean isConnected() {
        return socket != null;
    }


    public boolean hasProperSocket() {
        return socket == null || socket.getPlug() == this;
    }


    public boolean canHaveAsSocket(Socket<B2, B1> socket) {
        return socket != null && getOrientation().isOpposite(socket.getOrientation());
    }

    public void connect (Socket<B2, B1> socket) throws Exception {
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
