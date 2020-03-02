package Main.BlockStructure;

import Main.BlockStructure.Functionality.Functionality;
import Main.BlockStructure.Connections.*;

public class Block {

    private final Plug[] plugs;
    private final Socket[] sockets;
    private final Functionality functionality;

    public Block(Plug[] plugs, Socket[] sockets, Functionality functionality) {
        this.plugs = plugs;
        this.sockets = sockets;
        this.functionality = functionality;
    }

    public Plug getPlugAt(int index) {
        return plugs[index];
    }

    public Socket getSocketAt(int index) {
        return sockets[index];
    }

    public Functionality getFunctionality() {
        return functionality;
    }
}


