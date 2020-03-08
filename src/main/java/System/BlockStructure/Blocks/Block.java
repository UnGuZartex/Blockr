package System.BlockStructure.Blocks;
import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Functionality.BlockFunctionality;


public abstract class Block {

    private final int id;
    private final BlockFunctionality functionality;

    public boolean isAlreadyRan() {
        return alreadyRan;
    }

    private boolean alreadyRan = false;


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

    public abstract Block returnToClosestCavity();

    public void setAlreadyRan(boolean b) {
        alreadyRan = b;
    }

    public void reset() {
        alreadyRan = false;
        for(int i = 0; i < getSubConnectors().length; i++) {
            if (getSubConnectors()[i].isConnected()) {
                Block connectBlock = getSubConnectors()[i].getConnectedBlock();
                connectBlock.reset();
            }
        }
    }
}
