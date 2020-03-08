package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.Plug;
import System.BlockStructure.Connectors.Socket;
import System.BlockStructure.Functionality.BlockFunctionality;
import System.BlockStructure.Functionality.IFunctionality;

public abstract class FunctionalBlock extends Block {

    private final Plug bottomPlug;
    private final Socket topSocket;

    protected FunctionalBlock(int id, BlockFunctionality functionality) {
        super(id, functionality);
        bottomPlug = new Plug(this, Orientation.FACING_DOWN);
        topSocket = new Socket(this, Orientation.FACING_UP);
    }

    public Socket getTopSocket() {
        return topSocket;
    }

    public Plug getBottomPlug() {
        return bottomPlug;
    }

    @Override
    public boolean hasNext() {
        return bottomPlug.isConnected();
    }

    @Override
    public Block getNext() {
        return getBottomPlug().getConnectedBlock();
    }

    @Override
    public boolean canBeStarter() {
        return true;
    }
}
