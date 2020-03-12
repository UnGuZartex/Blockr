package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.BlockFunctionality;

import java.util.ArrayList;
import java.util.List;

public abstract class FunctionalBlock extends Block {

    private final MainConnector mainConnector;


    protected FunctionalBlock(BlockFunctionality functionality) {
        super(functionality);
         mainConnector = new MainConnector(this, Orientation.FACING_UP, Type.SOCKET);
         getSubConnectors().add(new SubConnector(this, Orientation.FACING_DOWN, Type.PLUG));

    }
    @Override
    public boolean hasNext() {
        return getSubConnectors().get(0).isConnected();
    }

    @Override
    public Block getNext() {
        return getSubConnectors().get(0).getConnectedBlock();
    }

    @Override
    public MainConnector getMainConnector() {
        return mainConnector;
    }


    @Override
    public Block returnToClosestCavity() {
        if (!mainConnector.isConnected()) {
            return this;
        }
        return mainConnector.getConnectedBlock().returnToClosestCavity();
    }
}
