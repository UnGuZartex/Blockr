package BlockStructure.Connectors;

import BlockStructure.Blocks.Block;

public class Socket extends Connector {

    private Plug plug;


    public Socket(Block block, Orientation orientation) {
        super(block, orientation);
    }

    public Plug getPlug() {
        return plug;
    }

    public boolean isConnected() {
        return plug != null;
    }

    public boolean hasProperPlug() {
        return plug == null || plug.getSocket() == null;
    }

    /**
     * Set the plug connected to this socket to the given plug.
     *
     * @param  plug
     *         The plug to connect to this socket.
     *
     * @pre    If the given card is effective,
     */
    public void connect (Plug plug) {
        assert plug == null || plug.getSocket() == this;
        assert plug != null || !isConnected() || this.plug.getSocket() == null;
        this.plug = plug;
    }

    public void disconnect() {
        connect(null);
    }
}
