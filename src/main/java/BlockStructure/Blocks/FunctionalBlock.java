package BlockStructure.Blocks;

import BlockStructure.Connectors.Orientation;
import BlockStructure.Connectors.Plug;
import BlockStructure.Connectors.Socket;
import BlockStructure.Functionality.Functionality;

public class FunctionalBlock extends Block {

    private final int id;
    private final Plug plug;
    private final Socket socket;

    public FunctionalBlock(int id, Functionality functionality) {
        super(functionality);
        this.id = id;
        this.plug = new Plug(this, Orientation.FACING_DOWN);
        this.socket = new Socket(this, Orientation.FACING_UP);
    }

    public Plug getPlug() {
        return plug;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public boolean hasNext() {
        return plug.isConnected();
    }

    @Override
    public Block getNext() {
        return plug.getSocket().getBlock();
    }
}
