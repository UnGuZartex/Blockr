package BlockStructure.Blocks;

import BlockStructure.Connectors.Orientation;
import BlockStructure.Connectors.Plug;
import BlockStructure.Connectors.Socket;
import BlockStructure.Functionality.Functionality;

public class Cavoc extends FunctionalBlock {

    private final Plug cavityPlug;
    private final Socket cavitySocket;
    private final Socket conditionalSocket;

    public Cavoc(int id, Functionality functionality) {
        super(id, functionality);
        cavityPlug = new Plug(this, Orientation.FACING_DOWN);
        cavitySocket = new Socket(this, Orientation.FACING_UP);
        conditionalSocket = new Socket(this, Orientation.FACING_RIGHT);
    }

    public Plug getCavityPlug() {
        return cavityPlug;
    }

    public Socket getCavitySocket() {
        return cavitySocket;
    }

    public Socket getConditionalSocket() {
        return conditionalSocket;
    }
}
