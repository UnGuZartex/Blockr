package BlockStructure.Factory;

import BlockStructure.Blocks.Block;
import BlockStructure.Blocks.FunctionalBlock;
import BlockStructure.Functionality.TurnLeftFunctionality;

public class TurnLeftBlockFactory extends BlockFactory {

    private static TurnLeftFunctionality functionality
            = new TurnLeftFunctionality();

    @Override
    protected Block getNewBlock() {
        return new FunctionalBlock(getID(), functionality);
    }
}
