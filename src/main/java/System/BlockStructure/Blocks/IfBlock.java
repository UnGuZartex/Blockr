package System.BlockStructure.Blocks;
import System.BlockStructure.Functionality.IfFunctionality;

public class IfBlock extends CavityBlock {

    private boolean skip = false;

    public IfBlock(int id) {
        super(id, new IfFunctionality());
    }
    @Override
    public boolean getSkip() {
        return skip;
    }

    @Override
    public boolean hasNext() {
        if (getFunctionality().getEvaluation() && !skip) {
            return getCavitySubConnector().isConnected();
        }
        else {
            return getSubConnectors()[0].isConnected();
        }
    }

    @Override
    public Block getNext() {
        if (getFunctionality().getEvaluation() && !skip) {
            System.out.println("Got here");
            skip = true;
            return getCavitySubConnector().getConnectedBlock();
        }
        else {
            System.out.println("Got here 2.0");
            skip = false;
            return getSubConnectors()[0].getConnectedBlock();
        }
    }
}
