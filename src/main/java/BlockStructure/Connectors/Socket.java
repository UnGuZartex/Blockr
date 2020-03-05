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


    protected void connect (Plug plug) throws Exception {
        if (this.isConnected()) {
            throw new Exception();
        }
        if (!plug.isConnected() && plug.getSocket() != this) {
            throw new Exception();
        }
        this.plug = plug;
    }

    protected void disconnect() throws Exception {
        if (plug.isConnected()) {
            throw new Exception();
        }
        plug = null;
    }
}
