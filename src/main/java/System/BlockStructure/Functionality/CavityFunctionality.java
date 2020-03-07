package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.CavityBlock;

public abstract class CavityFunctionality implements IFunctionality {

    private boolean evaluation;

    protected final CavityBlock block;

    protected CavityFunctionality(int blockId) {
        block = new CavityBlock(blockId, this);
    }

    protected void setEvaluation(boolean evaluation) {
        this.evaluation = evaluation;
    }

    public boolean getEvaluation() {
        return evaluation;
    }

    public CavityBlock getBlock(){
        return block;
    }
}
