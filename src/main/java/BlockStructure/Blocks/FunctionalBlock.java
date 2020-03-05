package BlockStructure.Blocks;

import BlockStructure.Connectors.Orientation;
import BlockStructure.Connectors.Socket;
import BlockStructure.Functionality.Functionality;

import java.util.function.Function;

public class FunctionalBlock extends Block {

    private final Socket socket;

    public FunctionalBlock(int id, Functionality functionality) {
        super(id, Orientation.FACING_DOWN, functionality);
        this.socket = new Socket(this, Orientation.FACING_UP);
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public FunctionalBlock getNext() {
        return (FunctionalBlock)getPlug().getSocket().getBlock();
    }
}
