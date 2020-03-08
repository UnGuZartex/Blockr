package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.WhileFunctionality;

public class WhileBlock extends CavityBlock {
    public WhileBlock(int id) {
        super(id, new WhileFunctionality());
    }

    @Override
    public Block returnToClosestCavity() {
        return this;
    }
}
