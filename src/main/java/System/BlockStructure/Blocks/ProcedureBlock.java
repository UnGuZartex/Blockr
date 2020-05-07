package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Functionality.DummyFunctionality;

public class ProcedureBlock extends Block {

    protected ProcedureBlock() {
        super(new DummyFunctionality());
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
}
