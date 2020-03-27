package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Functionality.BlockFunctionality;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class for blocks with a functionality.
 *
 * @author Alpha-team
 */
public abstract class Block {

    /**
     * Variable referring to the last cavity visited
     */
    private Block returnToBlock;

    /**
     * Variable referring to the functionality of this block.
     */
    private final BlockFunctionality functionality;
    /**
     * Variable referring to all the sub connectors of this block.
     */
    private final List<SubConnector> subConnector = new ArrayList<>();
    /**

    /**
     * Initialise a new block with given functionality
     *
     * @param functionality The functionality of this block.
     *
     * @post The functionality of this block is set to the given functionality.
     */
    protected Block(BlockFunctionality functionality) {
        this.functionality = functionality;
    }

    /**
     * Get the functionality of this block.
     *
     * @return The functionality of this block.
     */
    public BlockFunctionality getFunctionality() {
        return functionality;
    }

    /**
     * Get the sub connectors of this block.
     *
     * @return The sub connectors of this block.
     */
    protected List<SubConnector> getSubConnectors() {
        return subConnector;
    }

    /**
     * Get the sub connector of this block at the given index.
     *
     * @param index The index of the sub connector of this block to return.
     *
     * @return The sub connector of this block at the given index.
     */
    public SubConnector getSubConnectorAt(int index) {
        return subConnector.get(index);
    }

    /**
     * Get the number of sub connectors in this block.
     *
     * @return The number of sub connectors.
     */
    public int getNbSubConnectors() {
        return subConnector.size();
    }


    /**
     * Get the main connector of this block.
     *
     * @return The main connector of this block.
     */
    public abstract MainConnector getMainConnector();


    /**
     * Check whether or not this block has a next block.
     *
     * @return True if and only if this block has a next block.
     */
    public abstract boolean hasNext();

    /**
     * Get the block which should be executed after this block.
     *
     * @return The block which should be executed after this block.
     */
    public abstract Block getNext();

    /**
     * Reset this block and all its connected blocks.
     *
     * @post The already ran variable of this block is set to false
     *       and all connected blocks through sub connectors are
     *       reset.
     */
    public void reset() {
        setReturnToBlock(null);
        for(int i = 0; i < getSubConnectors().size(); i++) {
            if (getSubConnectors().get(i).isConnected()) {
                Block connectBlock = getSubConnectors().get(i).getConnectedBlock();
                connectBlock.reset();
            }
        }
    }

    /**
     * Check whether or not this block has proper connections.
     *
     * @return True if and only if this block has no following up blocks
     *         or its next block has valid following up blocks.
     */
    public boolean hasProperConnections() {
        return !hasNext() || getNext().hasProperConnections();
}


    /**
     * Finds the correct subconnector of a block given its ID.
     * @param ID the ID to search for
     * @return The connector in the block with this given ID.
     */
    public SubConnector getSubConnectorWithID(String ID) {
        System.out.println(ID);
        for (SubConnector connector:subConnector) {
            if (connector.getID().equals(ID)) {
                return connector;
            }
        }
        throw new IllegalStateException("This connection ID cannot exist for this block");
    }


    protected Block getReturnToBlock() {
        return returnToBlock;
    }

    protected void setReturnToBlock(Block returnToBlock) {
        this.returnToBlock = returnToBlock;
    }
}
