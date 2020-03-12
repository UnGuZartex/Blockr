package System.BlockStructure.Blocks;
import System.BlockStructure.Functionality.CavityFunctionality;

public class IfBlock extends CavityBlock {


    public IfBlock(CavityFunctionality cavityFunctionality) {
        super(cavityFunctionality);
    }

    @Override
    public Block returnToClosestCavity() {
        if (isAlreadyRan()) {
            return super.returnToClosestCavity();
        }
        return getNext();
    }


}
