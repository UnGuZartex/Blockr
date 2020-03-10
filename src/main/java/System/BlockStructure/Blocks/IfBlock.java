package System.BlockStructure.Blocks;
import System.BlockStructure.Functionality.IfFunctionality;

public class IfBlock extends CavityBlock {


    public IfBlock() {
        super(new IfFunctionality());
    }

    @Override
    public Block returnToClosestCavity() {
        if (isAlreadyRan()) {
            return super.returnToClosestCavity();
        }
        return getNext();
    }

    @Override
    public boolean hasNext() {
        if (getFunctionality().getEvaluation()) {
            return getCavitySubConnector().isConnected();
        }
        else {
            return getSubConnectors()[0].isConnected();
        }
    }
}
