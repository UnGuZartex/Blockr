package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.ConditionalBlockFunctionality;

public class OperationalBlock extends ConditionalBlock {

    private int counter;

    private final SubConnector[] subConnectors;

    public <B extends OperationalBlock> OperationalBlock(int id, ConditionalBlockFunctionality<B> functionality, int nbSubConnectors) {
        super(id, functionality);
        subConnectors = new SubConnector[nbSubConnectors];
        for(int i = 0; i < nbSubConnectors; i++) {
            subConnectors[i] = new SubConnector(this, Orientation.FACING_RIGHT, Type.SOCKET);
        }
    }

    public SubConnector getSocketAt(int index) {
        return getSubConnectors()[index];
    }

    @Override
    public Block getNext() {
        if (counter >= subConnectors.length) {
            counter = 0;
        }
        return subConnectors[counter++].getConnectedBlock();
    }

    @Override
    public SubConnector[] getSubConnectors() {
        return subConnectors;
    }
}
