package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.DummyFunctionality;

public class ProcedureCall extends FunctionalBlock {

    private ProcedureBlock procedure;

    public ProcedureCall(ProcedureBlock procedure) {
        super(new DummyFunctionality());
        this.procedure = procedure;
    }


    @Override
    public boolean hasNext() {
        return procedure != null;
    }

    @Override
    public Block getNext() {
        procedure.setReturnToBlock(getSubConnectorAt(0).getConnectedBlock());
        return procedure;
    }


    @Override
    public Block clone() {
        return new ProcedureCall(procedure);
    }

}
