package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.BlockFunctionality;

public abstract class FunctionalBlock extends Block {

    private final MainConnector mainConnector;

    private final SubConnector[] subConnector;

    protected FunctionalBlock(BlockFunctionality functionality) {
        super(functionality);
         mainConnector = new MainConnector(this, Orientation.FACING_UP, Type.SOCKET);
         subConnector = new SubConnector[]{new SubConnector(this, Orientation.FACING_DOWN, Type.PLUG)};

    }
    @Override
    public boolean hasNext() {
        return subConnector[0].isConnected();
    }

    @Override
    public Block getNext() {
        return subConnector[0].getConnectedBlock();
    }

    @Override
    public boolean canBeStarter() {
        return true;
    }

    @Override
    public MainConnector getMainConnector() {
        return mainConnector;
    }

    @Override
    public SubConnector[] getSubConnectors() {
        return subConnector;
    }

    @Override
    public Block returnToClosestCavity() {
        if (!mainConnector.isConnected()) {
            return this;
        }
        return mainConnector.getConnectedBlock().returnToClosestCavity();
    }
}
