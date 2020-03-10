package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.ConditionalBlockFunctionality;

import java.util.List;

public class OperationalBlock extends ConditionalBlock {

    private int counter;

    public <B extends OperationalBlock> OperationalBlock(ConditionalBlockFunctionality<B> functionality, int nbSubConnectors) {
        super(functionality);
        for(int i = 0; i < nbSubConnectors; i++) {
            getSubConnectors().add(new SubConnector(this, Orientation.FACING_RIGHT, Type.SOCKET));
        }
    }


    @Override
    public boolean hasNext() {

        return getSubConnectorAt(counter).isConnected();
    }

    @Override
    public Block getNext() {
        if (counter >= getSubConnectorListSize()) {
            counter = 0;
        }
        return getSubConnectorAt(counter++).getConnectedBlock();
    }

    @Override
    public boolean isValid() {
        if (!getMainConnector().isConnected()) {
            return false;
        }
        boolean toReturn = true;
        for (int i = 0; i < getSubConnectorListSize(); i++) {
            if (getSubConnectorAt(i).isConnected()) {
                toReturn = toReturn && getSubConnectorAt(i).getConnectedBlock().isValid();
            }
            else {
                return false;
            }
        }
        return toReturn;
    }
}
