package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.CavityFunctionality;

public class WhileBlock extends CavityBlock {
    public WhileBlock(CavityFunctionality functionality) {
        super(functionality);
    }

    @Override
    public Block returnToClosestCavity() {
        if (isAlreadyRan()) {
            return super.returnToClosestCavity();
        }
        return this;
    }
}
