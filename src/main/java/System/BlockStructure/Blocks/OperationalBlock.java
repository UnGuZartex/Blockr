package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.Socket;
import System.BlockStructure.Functionality.BlockFunctionality;
import System.BlockStructure.Functionality.ConditionalBlockFunctionality;

public class OperationalBlock extends ConditionalBlock {

    private final Socket[] sockets;
    private int counter;

    public <B extends OperationalBlock> OperationalBlock(int id, ConditionalBlockFunctionality<B> functionality, int nbSockets) {
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
