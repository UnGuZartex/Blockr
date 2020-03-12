package System.BlockStructure.Blocks;
import System.BlockStructure.Functionality.IfFunctionality;

public class IfBlock extends CavityBlock {


    public IfBlock(IfFunctionality ifFunctionality) {
        super(ifFunctionality);
    }

    @Override
    public Block returnToClosestCavity() {
        if (hasAlreadyRan()) {
            return super.returnToClosestCavity();
        }
        return getNext();
    }


}
