package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.Plug;
import System.BlockStructure.Connectors.Socket;
import System.BlockStructure.Functionality.CavityFunctionality;

public class CavityBlock extends FunctionalBlock<CavityFunctionality> {

    private final Plug<CavityBlock, FunctionalBlock<?>> cavityPlug;
    private final Socket<CavityBlock, FunctionalBlock<?>> cavitySocket;
    private final Socket<CavityBlock, ConditionalBlock> conditionalSocket;

    public CavityBlock(int id, CavityFunctionality functionality) {
        super(id, functionality);
        cavityPlug = new Plug<>(this, Orientation.FACING_DOWN);
        cavitySocket = new Socket<>(this, Orientation.FACING_UP);
        conditionalSocket = new Socket<>(this, Orientation.FACING_RIGHT);
    }

    public Plug<CavityBlock, FunctionalBlock<?>> getCavityPlug() {
        return cavityPlug;
    }

    public Socket<CavityBlock, FunctionalBlock<?>> getCavitySocket() {
        return cavitySocket;
    }

    public Socket<CavityBlock, ConditionalBlock> getConditionalSocket() {
        return conditionalSocket;
    }

    public ConditionalBlock getCondition() {
        return conditionalSocket.getConnectedConnector().getBlock();
    }

    @Override
    public boolean hasNext() {
        if (getFunctionality().getEvaluation()) {
            return cavitySocket.getConnectedConnector() != null;
        }
        else {
            return getBottomPlug().getConnectedConnector() != null;
        }
    }

    @Override
    public FunctionalBlock<?> getNext() {
        if (getFunctionality().getEvaluation()) {
            return cavitySocket.getConnectedBlock();
        }
        else {
            return getBottomPlug().getConnectedBlock();
        }
    }
}
