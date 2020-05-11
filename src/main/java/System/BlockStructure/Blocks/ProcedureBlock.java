package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.DummyFunctionality;

public class ProcedureBlock extends Block {

    public ProcedureBlock() {
        super(new DummyFunctionality());
        getSubConnectors().add(new SubConnector(this, Orientation.FACING_DOWN, Type.PLUG));
    }

    @Override
    public MainConnector getMainConnector() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return getSubConnectorAt(0).isConnected();
    }

    @Override
    public Block getNext() {
        return getSubConnectorAt(0).getConnectedBlock();
    }

    @Override
    public Block clone() {
        return new ProcedureBlock();
    }

    @Override
    public Block getBlockAtIndex(int index) {
        return getSubConnectorAt(0).getConnectedBlock().getBlockAtIndex(index - 1);
    }

    @Override
    public int getIndexOfBlock(Block block) {
        return getSubConnectorAt(0).getConnectedBlock().getIndexOfBlock(block) + 1;
    }

    @Override
    public boolean isIllegalExtraStartingBlock() {
        return false;
    }
}
