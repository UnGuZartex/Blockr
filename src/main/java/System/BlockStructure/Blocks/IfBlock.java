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

    public void setSkip(boolean b) {
        skip = b;
    }

    @Override
    public boolean hasNext() {
        if (getFunctionality().getEvaluation() && !skip) {
            return getCavityPlug().isConnected();
        }
        else {
            return getBottomPlug().isConnected();
        }
    }

    @Override
    public Block getNext() {
        if (getFunctionality().getEvaluation() && !skip) {
            System.out.println("Got here");
            skip = true;
            return getCavityPlug().getConnectedBlock();
        }
        else {
            System.out.println("Got here 2.0");

            skip = false;
            return getBottomPlug().getConnectedBlock();
        }
    }
}
