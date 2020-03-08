package System.BlockStructure.Blocks;
import System.BlockStructure.Functionality.BlockFunctionality;
import System.BlockStructure.Functionality.IFunctionality;


public abstract class Block {

    private final int id;
    private final BlockFunctionality functionality;

    protected Block(int id, BlockFunctionality functionality) {
        this.id = id;
        this.functionality = functionality;
    }

    public BlockFunctionality getFunctionality() {
        return functionality;
    }

    public abstract boolean hasNext();

    public abstract Block getNext();

    public abstract boolean canBeStarter();

    public boolean getSkip() {
        return false;
    }
}
