package System.BlockStructure.Blocks.Factory;


import System.BlockStructure.Blocks.CavityBlock;
import System.BlockStructure.Functionality.IfFunctionality;

public class IfBlockFactory extends BlockFactory {
    @Override
    public CavityBlock getNewBlock() {
        return new IfFunctionality(getID()).getBlock();
    }
}
