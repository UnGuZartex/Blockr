package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.DummyFunctionality;

public class ProcedureCall extends FunctionalBlock {

    private final ProcedureBlock procedure;

    public ProcedureCall(ProcedureBlock procedure) {
        super(new DummyFunctionality());
        this.procedure = procedure;
    }


    @Override
    public boolean hasProperConnections() {
        return !hasNext() || (procedure.hasProperConnections() && (!getSubConnectorAt(0).isConnected() || getSubConnectorAt(0).getConnectedBlock().hasProperConnections()));
    }

    @Override
    public boolean hasNext() {
        if (procedure != null) {
            return !procedure.isTerminated() && !procedure.isPassed();
        }
        return false;
    }

    @Override
    public Block getNext() {
        if (hasNext()) {
            setReturnToOfNext();
            return procedure;
        }
        return getReturnToBlock();
    }

    private void setReturnToOfNext() {
        if (getSubConnectorAt(0).isConnected()) {
            procedure.setReturnToBlock(getSubConnectorAt(0).getConnectedBlock());
            getSubConnectorAt(0).getConnectedBlock().setReturnToBlock(getReturnToBlock());
        } else {
            procedure.setReturnToBlock(getReturnToBlock());
        }
    }

    @Override
    public Block getBlockAtIndex(int index) {

        if (index < 0) {
            return null;
        }
        if (index == 0) {
            return this;
        }
        if (hasNext()) {
            Block backup = procedure.getReturnToBlock();
            procedure.setReturnToBlock(getSubConnectorAt(0).getConnectedBlock());
            Block toReturn = procedure.getBlockAtIndex(index-1);
            procedure.setReturnToBlock(backup);
            return toReturn;
        }
        if (getReturnToBlock() == null) {
            return null;
        }
        return getReturnToBlock().getBlockAtIndex(index - 1);
    }

    @Override
    public int getIndexOfBlock(Block block) { //TODO HIER IETS FIXEN

        if (block == null) {
            return -1;
        }
        if (block == this) {
            return 0;
        }
        if (hasNext()) {
            Block backup = procedure.getReturnToBlock();
            procedure.setReturnToBlock(getSubConnectorAt(0).getConnectedBlock());
            int toReturn = 1 + procedure.getIndexOfBlock(block);
            procedure.setReturnToBlock(backup);
            return toReturn;
        }
        if (getReturnToBlock() == null) {
            return -1;
        }
        return 1 + getReturnToBlock().getIndexOfBlock(block);
    }

    @Override
    public Block clone() {
        return new ProcedureCall(procedure);
    }

}
