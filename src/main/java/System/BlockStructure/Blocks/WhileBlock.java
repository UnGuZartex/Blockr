package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.WhileFunctionality;

public class WhileBlock extends CavityBlock {
    public WhileBlock(WhileFunctionality functionality) {
        super(functionality);
    }

    @Override
    public Block returnToClosestCavity() {
        if (hasAlreadyRan()) {
            return super.returnToClosestCavity();
        }
        return this;
    }
}
