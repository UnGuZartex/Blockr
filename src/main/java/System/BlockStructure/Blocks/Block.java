package System.BlockStructure.Blocks;
import System.BlockStructure.Functionality.BlockFunctionality;
import System.BlockStructure.Functionality.IFunctionality;


public abstract class Block {

    private final int id;
    private final BlockFunctionality<Block> functionality;

    protected Block(int id, BlockFunctionality<Block> functionality) {
        this.id = id;
        this.functionality = functionality;
        functionality.setBlock(this);
    }

    public BlockFunctionality<?> getFunctionality() {
        return functionality;
    }

    public abstract boolean hasNext();

    public abstract Block getNext();

    public abstract boolean canBeStarter();
}
