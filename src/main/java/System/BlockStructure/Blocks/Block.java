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
     * Variable referring to the functionality of this block.
     */
    private final BlockFunctionality functionality;
    /**
     * Variable referring to all the sub connectors of this block.
     */
    private final List<SubConnector> subConnector = new ArrayList<>();
    /**
     * Variable referring to whether or not this block has already ran.
     */
    private boolean alreadyRan = false;

    /**
     * Initialise a new block with given functionality
     *
     * @param functionality The functionality of this block.
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
     * Check whether or not this block has already ran.
     *
     * @return True if and only if this block has already ran.
     */
    public boolean hasAlreadyRan() {
        return alreadyRan;
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
     * Return to the closest cavity to execute.
     *
     * @return The cavity in which this block is contained.
     */
    public abstract Block returnToClosestCavity();

    /**
     * Set whether or not this block has already ran.
     *
     * @param alreadyRan The new value of already ran.
     */
    public void setAlreadyRan(boolean alreadyRan) {
        this.alreadyRan = alreadyRan;
    }

    /**
     * Reset this block and all its connected blocks.
     */
    public void reset() {
        alreadyRan = false;
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
}
