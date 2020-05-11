package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.DummyFunctionality;

public class ProcedureCall extends FunctionalBlock {

    private final ProcedureBlock procedure;

    public ProcedureCall(ProcedureBlock procedure) {
        super(new DummyFunctionality());
        this.procedure = procedure;
    }


    @Override
    public boolean hasNext() {
        if (procedure != null) {
            return !procedure.isTerminated();
        }
        return false;
    }

    @Override
    public Block getNext() {
        if (hasNext()) {
            procedure.setReturnToBlock(getSubConnectorAt(0).getConnectedBlock());
            return procedure;
        }
        return getReturnToBlock();
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
            procedure.setReturnToBlock(getSubConnectorAt(0).getConnectedBlock());
            return procedure.getBlockAtIndex(index - 1);
        }
        if (getReturnToBlock() == null) {
            return null;
        }
        return getReturnToBlock().getBlockAtIndex(index - 1);    }

    @Override
    public int getIndexOfBlock(Block block) {
        if (block == null) {
            return -1;
        }
        if (block == this) {
            return 0;
        }
        if (hasNext()) {
            procedure.setReturnToBlock(getSubConnectorAt(0).getConnectedBlock());
            return 1 + procedure.getIndexOfBlock(block);
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
