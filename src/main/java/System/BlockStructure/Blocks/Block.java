package System.BlockStructure.Blocks;
import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Functionality.BlockFunctionality;


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


    public abstract MainConnector getMainConnector();

    public abstract SubConnector[] getSubConnectors();

    public abstract boolean canBeStarter();

    public boolean getSkip() {
        return false;
    }

    public abstract Block returnToClosestCavity();
}
