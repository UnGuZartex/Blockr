package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.ConditionalBlockFunctionality;

/**
 * A class for operational blocks. These are blocks which have several
 * conditions in them and execute not, or ... operations on these.
 *
 * @author Alpha-team
 */
public class OperationalBlock extends ConditionalBlock {

    /**
     * Variable referring to the current condition which should be checked. By
     * default this is set on 1.
     */
    private int counter;

    /**
     * Initialise a new operational block with given functionality and number
     * of sub connectors.
     *
     * @param functionality The functionality of this operational block.
     * @param nbSubConnectors The number of sub connectors this operation should
     *                        have, thus the number of inner statements.
     * @param <B> The type of block the functionality should belong to.
     */
    public <B extends OperationalBlock> OperationalBlock(ConditionalBlockFunctionality<B> functionality, int nbSubConnectors) {
        super(functionality);
        for(int i = 0; i < nbSubConnectors; i++) {
            getSubConnectors().add(new SubConnector("SUB_"+i,this, Orientation.FACING_RIGHT, Type.SOCKET));
        }
    }

    /**
     * Checks whether or not this operation has a connected conditional on the
     * socket at the index of the counter of this operation.
     *
     * @return True if and only if the the sub connector at the counter of this
     *         operation is connected.
     */
    @Override
    public boolean hasNext() {
        return getSubConnectorAt(counter).isConnected();
    }

    /**
     * Get the block at the sub connector of the counter. The counter is also updated.
     *
     * @return The block connected at the sub connector at the index of the
     *         counter of this operational block.
     */
    @Override
    public Block getNext() {
        counter %= getNbSubConnectors();
        return getSubConnectorAt(counter++).getConnectedBlock();
    }

    /**
     * Check if this operational block has proper connections.
     *
     * @return True if and only if this operational block has
     *         a block connected to it's main connector, and all
     *         of it's sub connectors have proper connections.
     */
    @Override
    public boolean hasProperConnections() {
        if (!getMainConnector().isConnected()) {
            return false;
        }
        boolean toReturn = true;
        for (int i = 0; i < getNbSubConnectors(); i++) {
            if (getSubConnectorAt(i).isConnected()) {
                toReturn = toReturn && getSubConnectorAt(i).getConnectedBlock().hasProperConnections();
            }
            else {
                return false;
            }
        }
        return toReturn;
    }
}
