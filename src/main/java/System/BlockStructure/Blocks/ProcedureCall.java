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
        return null;
    }

    @Override
    public Block clone() {
        return new ProcedureCall(procedure);
    }

}
