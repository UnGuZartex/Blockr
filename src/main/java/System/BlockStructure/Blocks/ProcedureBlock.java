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
        if (hasNext()) {
            Block nextBlock = getSubConnectorAt(0).getConnectedBlock();
            nextBlock.setReturnToBlock(getReturnToBlock());
            return nextBlock;
        }
        return getReturnToBlock();
    }

    @Override
    public Block clone() {
        return new ProcedureBlock();
    }

    @Override
    public Block getBlockAtIndex(int index) {
        if (index < 0) {
            return null;
        }
        if (index == 0) {
            return this;
        }
        if (getSubConnectorAt(0).isConnected()) {
            getSubConnectorAt(0).getConnectedBlock().setReturnToBlock(null);
            return getSubConnectorAt(0).getConnectedBlock().getBlockAtIndex(index - 1);
        }
        if (getReturnToBlock() == null) {
            return null;
        }
        return getReturnToBlock().getBlockAtIndex(index - 1);
    }

    @Override
    public int getIndexOfBlock(Block block) {
        if (block == null) {
            return -1;
        }
        if (block == this) {
            return 0;
        }
        if (getSubConnectorAt(0).isConnected()) {
            getSubConnectorAt(0).getConnectedBlock().setReturnToBlock(null);
            return 1 + getSubConnectorAt(0).getConnectedBlock().getIndexOfBlock(block);
        }
        if (getReturnToBlock() == null) {
            return -1;
        }
        return 1 + getReturnToBlock().getIndexOfBlock(block);
    }

    @Override
    public boolean isIllegalExtraStartingBlock() {
        return false;
    }


}
