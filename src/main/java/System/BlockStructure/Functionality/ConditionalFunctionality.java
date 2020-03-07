package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.ConditionalBlock;

public abstract class ConditionalFunctionality implements IFunctionality {

    private boolean evaluation;

    protected final ConditionalBlock block;

    protected ConditionalFunctionality(int blockId) {
           block = new ConditionalBlock(blockId, this);
    }

    protected void setEvaluation(boolean evaluation) {
        this.evaluation = evaluation;
    }

    public boolean getEvaluation() {
        return evaluation;
    }

    public ConditionalBlock getBlock(){
        return block;
    }
}
