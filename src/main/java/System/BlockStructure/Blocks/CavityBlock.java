package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.Plug;
import System.BlockStructure.Connectors.Socket;
import System.BlockStructure.Functionality.CavityFunctionality;
import System.BlockStructure.Functionality.ConditionalFunctionality;

public class CavityBlock extends BasicBlock<CavityFunctionality> {

    private final Plug<CavityBlock, BasicBlock<?>> cavityPlug;
    private final Socket<CavityBlock, BasicBlock<?>> cavitySocket;
    private final Socket<CavityBlock, ConditionalBlock> conditionalSocket;

    public CavityBlock(int id, CavityFunctionality functionality) {
        super(id, functionality);
        cavityPlug = new Plug<>(this, Orientation.FACING_DOWN);
        cavitySocket = new Socket<>(this, Orientation.FACING_UP);
        conditionalSocket = new Socket<>(this, Orientation.FACING_RIGHT);
    }

    public Plug<CavityBlock, BasicBlock<?>> getCavityPlug() {
        return cavityPlug;
    }

    public Socket<CavityBlock, BasicBlock<?>> getCavitySocket() {
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
        CavityFunctionality func = getFunctionality();
        if (func.getEvaluation()) {
            return cavitySocket.getConnectedConnector() != null;
        }
        else {
            return getBottomPlug().getConnectedConnector() != null;
        }
    }

    @Override
    public BasicBlock getNext() {
        CavityFunctionality func = getFunctionality();
        if (func.getEvaluation()) {
            return cavitySocket.getConnectedConnector().getBlock();
        }
        else {
            return getBottomPlug().getConnectedConnector().getBlock();
        }
    }
}
