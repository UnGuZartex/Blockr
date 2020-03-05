package BlockStructure.Blocks;

import BlockStructure.Connectors.Orientation;
import BlockStructure.Connectors.Socket;
import BlockStructure.Functionality.Functionality;

public class OperationalBlock extends ConditionalBlock {

    private final Socket[] sockets;
    private int counter;

    public OperationalBlock(Functionality functionality, int nbSockets) {
        super(functionality);
        this.sockets = new Socket[nbSockets];
        for (int i = 0; i < nbSockets; i++) {
            sockets[i] = new Socket(this, Orientation.FACING_RIGHT);
        }
        this.counter = 0;
    }


    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Block getNext() {
        return null;
    }
}
