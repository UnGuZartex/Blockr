package Main.BlockStructure.Connections;

import Main.BlockStructure.*;
import Main.BlockStructure.ConnectionComponents.*;

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


