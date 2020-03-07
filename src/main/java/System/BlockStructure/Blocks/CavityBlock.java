package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.Plug;
import System.BlockStructure.Connectors.Socket;
import System.BlockStructure.Functionality.BlockFunctionality;

public abstract class CavityBlock extends FunctionalBlock {

    private final Plug cavityPlug;
    private final Socket cavitySocket;
    private final Socket conditionalSocket;

    public CavityBlock(int id, BlockFunctionality functionality) {
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

    public Block getCondition() {
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
    public Block getNext() {
        if (getFunctionality().getEvaluation()) {
            return cavitySocket.getConnectedBlock();
        }
        else {
            return getBottomPlug().getConnectedBlock();
        }
    }
}
