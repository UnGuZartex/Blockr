package System.BlockStructure.Blocks;
import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Functionality.BlockFunctionality;

import java.util.ArrayList;
import java.util.List;


public abstract class Block {

    private final BlockFunctionality functionality;

    private final List<SubConnector> subConnector = new ArrayList<>();

    public boolean isAlreadyRan() {
        return alreadyRan;
    }

    private boolean alreadyRan = false;


    protected Block(BlockFunctionality functionality) {
        this.functionality = functionality;
    }

    public BlockFunctionality getFunctionality() {
        return functionality;
    }

    public abstract boolean hasNext();

    public abstract Block getNext();


    public abstract MainConnector getMainConnector();

    protected List<SubConnector> getSubConnectors() {
        return subConnector;
    }

    public abstract Block returnToClosestCavity();

    public void setAlreadyRan(boolean b) {
        alreadyRan = b;
    }

    public void reset() {
        alreadyRan = false;
        for(int i = 0; i < getSubConnectors().size(); i++) {
            if (getSubConnectors().get(i).isConnected()) {
                Block connectBlock = getSubConnectors().get(i).getConnectedBlock();
                connectBlock.reset();
            }
        }
    }

    public boolean isValid() {
        if (hasNext()) {
            return getNext().isValid();
        }
        else {
            return true;
        }
    }

    public int getSubConnectorListSize() {
        return subConnector.size();
    }

    public SubConnector getSubConnectorAt(int index) {
        return subConnector.get(index);
    }
}
