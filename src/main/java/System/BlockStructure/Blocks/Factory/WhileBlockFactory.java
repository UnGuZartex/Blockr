package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.CavityBlock;
import System.BlockStructure.Functionality.WhileFunctionality;

public class WhileBlockFactory extends BlockFactory {
    @Override
    public CavityBlock getNewBlock() {
        return new WhileFunctionality(getID()).getBlock();
    }
}
