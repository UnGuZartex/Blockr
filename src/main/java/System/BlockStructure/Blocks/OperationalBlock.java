package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.Socket;
import System.BlockStructure.Functionality.BlockFunctionality;

public class OperationalBlock extends ConditionalBlock {

    private final Socket[] sockets;
    private int counter;

    public OperationalBlock(int id, BlockFunctionality functionality, int nbSockets) {
        super(id, functionality);
        this.sockets = new Socket[nbSockets];
        for (int i = 0; i < nbSockets; i++) {
            sockets[i] = new Socket(this, Orientation.FACING_RIGHT);
        }
        counter = 0;
    }

    public Socket getSocketAt(int index) {
        return sockets[index];
    }

    @Override
    public Block getNext() {
        if (counter >= sockets.length) {
            counter = 0;
        }
        return sockets[counter++].getConnectedBlock();
    }
}
