package BlockStructure.Blocks.Factory;

import BlockStructure.Blocks.Block;
import BlockStructure.Blocks.FunctionalBlock;
import BlockStructure.Functionality.MoveForwardFunctionality;

public class MoveForwardBlockFactory extends BlockFactory {

    private MoveForwardFunctionality functionality
            = new MoveForwardFunctionality();

    @Override
    protected Block getNewBlock() {
        return new FunctionalBlock(getID(), functionality);
    }
}
