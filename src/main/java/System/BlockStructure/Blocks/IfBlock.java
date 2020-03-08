package System.BlockStructure.Blocks;
import System.BlockStructure.Functionality.IfFunctionality;

public class IfBlock extends CavityBlock {


    public IfBlock(int id) {
        super(id, new IfFunctionality());
    }

    @Override
    public Block returnToClosestCavity() {
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
