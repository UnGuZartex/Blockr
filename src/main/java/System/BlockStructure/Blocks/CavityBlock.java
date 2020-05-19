package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.ConditionalBlockFunctionality;

import java.util.Stack;

/**
 * An abstract class for blocks which have a cavity. These blocks
 * also can have a condition connected to it.
 *
 * @author Alpha-team
 */
public abstract class CavityBlock extends FunctionalBlock {

    /**
     * Variable referring to the sub connector connecting the cavity.
     */
    protected final SubConnector cavitySubConnector;
    /**
     * Variable referring to the sub connector connecting the conditional.
     */
    private final SubConnector conditionalSubConnector;

    /**
     * Initialise a new cavity block with given functionality. It's sub connectors
     * are created and added to the sub connector list.
     *
     * @param functionality The functionality of this block.
     * @param <B> The block type this functionality belongs to.
     *
     * @effect Call super constructor with the given functionality.
     * @effect Set the cavity connector to a new sub connector which is facing down
     *         and has type plug.
     * @effect Set the conditional connector to a new sub connector which is facing
     *         right and has the type socket.
     * @effect Add the cavity connector to the sub connector list.
     * @effect Add the conditional connector to the sub connector list.
     */
    protected <B extends CavityBlock> CavityBlock(ConditionalBlockFunctionality<B> functionality) {
        super(functionality);
        functionality.setBlock((B) this);
        cavitySubConnector = new SubConnector(this, Orientation.FACING_DOWN, Type.PLUG);
        conditionalSubConnector = new SubConnector(this, Orientation.FACING_RIGHT, Type.SOCKET);
        getSubConnectors().add(cavitySubConnector);
        getSubConnectors().add(conditionalSubConnector);
    }

    /**
     * Get the sub connector regarding the cavity.
     *
     * @return The sub connector regarding the cavity.
     */
    public SubConnector getCavitySubConnector() {
        return cavitySubConnector;
    }

    /**
     * Get the sub connector regarding the conditional.
     *
     * @return The sub connector regarding the conditional.
     */
    public SubConnector getConditionalSubConnector() {
        return conditionalSubConnector;
    }

    /**
     * Get the condition connected to this cavity block.
     *
     * @return The condition connected to this cavity block.
     */
    public Block getCondition() {
        return conditionalSubConnector.getConnectedBlock();
    }

    /**
     * Checks whether this cavity block has a next block.
     *
     * @return True if and only if there is a block connected to the proper
     *         sub connector. If the evaluation of this block's functionality
     *         is true, then the cavity sub connector is checked, otherwise
     *         is the super has next function called.
     */
    @Override
    public boolean hasNext() {
        if (getFunctionality().getEvaluation()) {
            return getCavitySubConnector().isConnected();
        }
        else {
            return super.hasNext();
        }
    }

    /**
     * Checks whether this cavity block has proper connections.
     *
     * @return True if and only if this connector has a condition connected to it
     *         which has proper connections, and if there is a cavity connected,
     *         the blocks in this should have proper connections, if there is a
     *         block connected under this cavity block, it should also have proper
     *         connections. If one of these conditions isn't met, then false is
     *         returned.
     */
    @Override
    public boolean hasProperConnections(Stack<Block> systemStack) {
        // Valid condition
        if (getConditionalSubConnector().getConnectedBlock() == null || !getConditionalSubConnector().getConnectedBlock().hasProperConnections(systemStack)) {
            return false;
        }

        // Valid cavity
        if (getCavitySubConnector().getConnectedBlock() != null && !getCavitySubConnector().getConnectedBlock().hasProperConnections(systemStack)) {
            return false;
        }

        // Valid blocks under the block
        if (getSubConnectorAt(0).getConnectedBlock() != null && !getSubConnectorAt(0).getConnectedBlock().hasProperConnections(systemStack)) {
            return false;
        }

        // All connected blocks are valid
        return true;
    }

    /**
     * Get the next block to execute, this depends on the evaluation of the condition of this block.
     * If the condition is true, the next block is the first in the cavity, otherwise, the next block
     * is the first block under the while.
     *
     * @return The next block to execute.
     *
     * @throws IllegalStateException
     *         If this block is terminated.
     */
    @Override
    public Block getNext(Stack<Block> systemStack) throws IllegalStateException {
        if (isTerminated()) {
            throw new IllegalStateException("This block is terminated!");
        }
        if (getFunctionality().getEvaluation()) {
            Block toPush = getNewReturnBlock();
            if (toPush != null) {
                systemStack.push(getNewReturnBlock());
            }
            if (hasNext()) {
                return getCavitySubConnector().getConnectedBlock();
            }
        }
        return super.getNext(systemStack);
    }

    /**
     * Get the new return to block.
     *
     * @return The new block to return to. 
     */
    protected abstract Block getNewReturnBlock();
}
