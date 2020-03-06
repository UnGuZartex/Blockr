package BlockStructure.Connectors;

import BlockStructure.Blocks.Block;

public class Socket<B1 extends Block, B2 extends Block> extends Connector<B1, B2> {

    private Plug<B2, B1> plug;

    public Socket(B1 block, Orientation orientation) {
        super(block, orientation);
    }

    public Plug<B2, B1> getPlug() {
        return plug;
    }

    public boolean isConnected() {
        return plug != null;
    }

    public boolean hasProperPlug() {
        return plug == null || plug.getSocket() == null;
    }


    protected void connect (Plug<B2, B1> plug) throws Exception {
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
