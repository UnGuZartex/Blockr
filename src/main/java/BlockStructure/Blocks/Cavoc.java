package BlockStructure.Blocks;

import BlockStructure.Connectors.Orientation;
import BlockStructure.Connectors.Plug;
import BlockStructure.Connectors.Socket;
import BlockStructure.Functionality.ConditionalFunctionality;

public class Cavoc<F extends ConditionalFunctionality> extends FunctionalBlock<F> {

    private final Plug<FunctionalBlock<?>, FunctionalBlock<?>> cavityPlug;
    private final Socket<FunctionalBlock<?>, FunctionalBlock<?>> cavitySocket;
    private final Socket<FunctionalBlock<?>, ConditionalBlock<?>> conditionalSocket;

    public Cavoc(int id, F functionality) {
        super(id, functionality);
        cavityPlug = new Plug<>(this, Orientation.FACING_DOWN);
        cavitySocket = new Socket<>(this, Orientation.FACING_UP);
        conditionalSocket = new Socket<>(this, Orientation.FACING_RIGHT);
    }

    public Plug<FunctionalBlock<?>, FunctionalBlock<?>> getCavityPlug() {
        return cavityPlug;
    }

    public Socket<FunctionalBlock<?>, FunctionalBlock<?>> getCavitySocket() {
        return cavitySocket;
    }

    public Socket<FunctionalBlock<?>, ConditionalBlock<?>> getConditionalSocket() {
        return conditionalSocket;
    }

    public ConditionalBlock<?> getCondition() {
        return conditionalSocket.getPlug().getBlock();
    }

    @Override
    public boolean hasNext() {
        ConditionalFunctionality func = getFunctionality();
        if (func.getEvaluation()) {
            return cavitySocket.getPlug().getBlock() != null;
        }
        else {
            return getBottomPlug().getSocket().getBlock() != null;
        }
    }

    @Override
    public FunctionalBlock<?> getNext() {
        ConditionalFunctionality func = getFunctionality();
        if (func.getEvaluation()) {
            return cavitySocket.getPlug().getBlock();
        }
        else {
            return getBottomPlug().getSocket().getBlock();
        }
    }


}
