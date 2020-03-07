package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.Plug;
import System.BlockStructure.Connectors.Socket;
import System.BlockStructure.Functionality.IFunctionality;

public class FunctionalBlock<F extends IFunctionality> extends Block<F> {

    private final Plug<FunctionalBlock<?>, FunctionalBlock<?>> bottomPlug;
    private final Socket<FunctionalBlock<?>, FunctionalBlock<?>> topSocket;

    public FunctionalBlock(int id, F functionality) {
        super(id, functionality);
        bottomPlug = new Plug<>(this, Orientation.FACING_DOWN);
        topSocket = new Socket<>(this, Orientation.FACING_UP);
    }

    public Socket<FunctionalBlock<?>, FunctionalBlock<?>> getTopSocket() {
        return topSocket;
    }

    public Plug<FunctionalBlock<?>, FunctionalBlock<?>> getBottomPlug() {
        return bottomPlug;
    }

    @Override
    public boolean hasNext() {
        return bottomPlug.getBlock() != null;
    }

    @Override
    public FunctionalBlock<?> getNext() {
        return getBottomPlug().getConnectedConnector().getBlock();
    }

    @Override
    public boolean canBeStarter() {
        return true;
    }
}
