package BlockStructure.Blocks;

import BlockStructure.Connectors.Orientation;
import BlockStructure.Connectors.Socket;
import BlockStructure.Functionality.ConditionalFunctionality;

public class OperationalBlock<F extends ConditionalFunctionality> extends ConditionalBlock<F> {

    private final Socket[] sockets;
    private int counter;

    public OperationalBlock(int id, F functionality, int nbSockets) {
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
    public ConditionalBlock getNext() {
        if (counter >= sockets.length) {
            counter = 0;
        }
        return (ConditionalBlock)sockets[counter++].getPlug().getBlock();
    }
}
