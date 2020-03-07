package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.Block;
import System.GameWorld.Level.Level;

public abstract class BlockFunctionality<B extends Block> implements IFunctionality {

    protected boolean evaluation;
    protected B block;

    public BlockFunctionality() {}

    public void setBlock(B block) {
        if (this.block == null) {
            this.block = block;
        }
    }

    public boolean getEvaluation() {
        return evaluation;
    }

    @Override
    public abstract void evaluate(Level level);
}
