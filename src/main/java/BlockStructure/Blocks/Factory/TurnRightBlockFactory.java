package BlockStructure.Blocks.Factory;

import BlockStructure.Blocks.Block;
import BlockStructure.Blocks.FunctionalBlock;
import BlockStructure.Functionality.TurnRightFunctionality;

public class TurnRightBlockFactory extends BlockFactory{

    private TurnRightFunctionality functionality
            = new TurnRightFunctionality();

    @Override
    protected Block getNewBlock() {
        return new FunctionalBlock(getID(), functionality);
    }
}
