package Main.BlockStructure.Connections;

import Main.BlockStructure.Block;
import Main.BlockStructure.ConnectionComponents.Plug;
import Main.BlockStructure.ConnectionComponents.Socket;
import Main.BlockStructure.Functionality.Condition;

public class CavityConnector extends BlockConnectors {

    private Plug cavityPlug;
    private Socket cavitySocket;
    private Condition condition;


    public CavityConnector(Plug plug, Socket socket, Plug cavityPlug, Socket cavitySocket, Condition condition) {
        super(plug, socket);
        this.cavityPlug = cavityPlug;
        this.cavitySocket = cavitySocket;
        this.condition = condition;
    }

    @Override
    public Block getNext() {
        if (condition.getEvaluation()){
            return cavityPlug.getSocket().getBlock();
        } else {
            return super.getNext();
        }
    }

}
