package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.Socket;
import System.BlockStructure.Functionality.ConditionalFunctionality;

public class OperationalBlock extends ConditionalBlock {

    private final Socket<OperationalBlock, ConditionalBlock>[] sockets;
    private int counter;

    public OperationalBlock(int id, ConditionalFunctionality functionality, int nbSockets) {
        super(id, functionality);
        this.sockets = new Socket[nbSockets];
        for (int i = 0; i < nbSockets; i++) {
            sockets[i] = new Socket<>(this, Orientation.FACING_RIGHT);
        }
        counter = 0;
    }

    public Socket getSocketAt(int index) {
        return sockets[index];
    }

    @Override
    public ConditionalBlock getNext() {
        if (counter >= sockets.length) {
            counter = 0;
        }
        return sockets[counter++].getConnectedConnector().getBlock();
    }
}
