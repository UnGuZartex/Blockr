package System.BlockStructure.Blocks;
import System.BlockStructure.Functionality.IfFunctionality;

public class IfBlock extends CavityBlock {
    public IfBlock(int id) {
        super(id, new IfFunctionality());
    }
}
