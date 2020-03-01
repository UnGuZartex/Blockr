package Main.BlockStructure.Blocks;

import Main.BlockStructure.Connections.*;

public class Block {

    private Plug[] plugs;
    private Socket[] sockets;


    public Block(Plug[] plugs, Socket[] sockets) {
        this.plugs = plugs;
        this.sockets = sockets;
    }

    public Plug getPlugAt(int index) {
        return plugs[index];
    }

    public Socket getSocketAt(int index) {
        return sockets[index];
    }
}


