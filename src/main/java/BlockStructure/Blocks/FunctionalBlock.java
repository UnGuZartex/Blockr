package BlockStructure.Blocks;

import BlockStructure.Connectors.Orientation;
import BlockStructure.Connectors.Plug;
import BlockStructure.Connectors.Socket;
import BlockStructure.Functionality.Functionality;

import java.util.function.Function;

public class FunctionalBlock<F extends Functionality> extends Block<F> {

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
}
