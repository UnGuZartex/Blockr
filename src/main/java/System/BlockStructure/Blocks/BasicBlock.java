package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.Plug;
import System.BlockStructure.Connectors.Socket;
import System.BlockStructure.Functionality.IFunctionality;

public class BasicBlock<F extends IFunctionality> extends Block<F> {

    private final Plug<BasicBlock<?>, BasicBlock<?>> bottomPlug;
    private final Socket<BasicBlock<?>, BasicBlock<?>> topSocket;

    public BasicBlock(int id, F functionality) {
        super(id, functionality);
        bottomPlug = new Plug<>(this, Orientation.FACING_DOWN);
        topSocket = new Socket<>(this, Orientation.FACING_UP);
    }

    public Socket<BasicBlock<?>, BasicBlock<?>> getTopSocket() {
        return topSocket;
    }

    public Plug<BasicBlock<?>, BasicBlock<?>> getBottomPlug() {
        return bottomPlug;
    }

    @Override
    public boolean hasNext() {
        return bottomPlug.getBlock() != null;
    }

    @Override
    public BasicBlock<?> getNext() {
        return getBottomPlug().getConnectedConnector().getBlock();
    }
}
