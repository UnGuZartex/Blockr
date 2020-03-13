package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.WhileFunctionality;

public class WhileBlock extends CavityBlock {
    public WhileBlock(WhileFunctionality functionality) {
        super(functionality);
    }

    @Override
    public Block getNextIfNone() {
        if (hasAlreadyRan()) {
            return super.getNextIfNone();
        }
        return this;
    }
}
