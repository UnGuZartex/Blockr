package System.BlockStructure.Blocks.Factory;


import System.BlockStructure.Blocks.IfBlock;
import System.BlockStructure.Functionality.IfFunctionality;

public class IfBlockFactory extends BlockFactory {
    @Override
    public IfBlock getNewBlock() {
        return new IfBlock(getID());
    }
}
