package System.BlockStructure.Blocks;
import System.BlockStructure.Functionality.IfFunctionality;

public class IfBlock extends CavityBlock {


    public IfBlock(IfFunctionality ifFunctionality) {
        super(ifFunctionality);
    }

    @Override
    public Block returnToClosestCavity() {
        if (isAlreadyRan()) {
            return super.returnToClosestCavity();
        }
        return getNext();
    }


}
