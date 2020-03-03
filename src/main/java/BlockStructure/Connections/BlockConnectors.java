package BlockStructure.Connections;

import BlockStructure.*;
import BlockStructure.ConnectionComponents.*;

public class BlockConnectors {

    private final Plug plug;
    private final Socket socket;

    public BlockConnectors(Plug plug, Socket socket) {
        this.plug = plug;
        this.socket = socket;
    }

    public Block getNext() {
        return plug.getSocket().getBlock();
    }
}


