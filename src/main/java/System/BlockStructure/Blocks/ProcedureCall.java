package System.BlockStructure.Blocks;

import Controllers.ListenerInterfaces.CallListener;
import System.BlockStructure.Functionality.DummyFunctionality;
import System.Logic.ProgramArea.Utility.ExecutionStack;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for procedure calls which can call a procedure.
 *
 * @invar Each procedure call must have a valid procedure.
 *        | isValidProcedure(procedure)
 *
 * @author Alpha-team
 */
public class ProcedureCall extends FunctionalBlock implements CallListener {

    /**
     * Variable referring to the procedure this call refers to.
     */
    private final ProcedureBlock procedure;
    /**
     * Variable referring to all the listeners of this block.
     */
    private final List<CallListener> listeners = new ArrayList<>();

    /**
     * Initialise a new procedure call.
     *
     * @param procedure The procedure for this call.
     *
     * @effect The super constructor is called with a dummy functionality.
     *
     * @post The procedure is set to the given procedure if it is valid.
     *
     * @throws IllegalArgumentException
     *         If the given procedure is not valid.
     */
    public ProcedureCall(ProcedureBlock procedure) throws IllegalArgumentException {
        super(new DummyFunctionality());
        if (!isValidProcedure(procedure)) {
            throw new IllegalArgumentException("The given procedure is invalid!");
        }
        this.procedure = procedure;
    }

    /**
     * Check whether or not the given procedure is valid.
     *
     * @param procedure The procedure to check.
     *
     * @return True if and only if the given procedure is not null.
     */
    public static boolean isValidProcedure(ProcedureBlock procedure) {
        return procedure != null;
    }

    /**
     * Get the procedure of this procedure call.
     *
     * @return The procedure of this procedure call.
     */
    public ProcedureBlock getProcedure() {
        return procedure;
    }

    /**
     * Subscribe the given listener as a listener of this block.
     *
     * @param listener The listener to subscribe.
     */
    public void subscribe(CallListener listener) {
        listeners.add(listener);
    }

    /**
     * Unsibscribe the given listener as a listener of this block.
     *
     * @param listener The listener to unsubscribe.
     */
    public void unSubscribe(CallListener listener) {
        listeners.remove(listener);
    }

    /**
     * Terminate this block.
     *
     * @effect Terminate this block.
     * @effect Notify that the procedure has been deleted.
     */
    @Override
    public void terminate() {
        super.terminate();
        notifyProcedureDeleted();
    }

    /**
     * Checks whether or not this block has valid connections.
     *
     * @return If this block has no next, then true is returned. Otherwise is checked whether or not
     *         the procedure has proper connections and the block on the bottom of this call is proper
     *         (meaning either such block doesn't exist or has proper connections).
     */
    @Override
    public boolean hasProperConnections() {
        return !hasNext() || (procedure.hasProperConnections() && (!getSubConnectorAt(0).isConnected() || getSubConnectorAt(0).getConnectedBlock().hasProperConnections()));
    }

    /**
     * Checks whether or not this block has a next block
     *
     * @return True if and only if the given procedure is not terminated and not passed.
     */
    @Override
    public boolean hasNext() {
        return !procedure.isTerminated();
    }

    /**
     * Get the block at the given index.
     *
     * @param index The index of the block to get.
     * @param systemStack The stack to use in the block calculation.
     *
     * @return The block at the given index, if this block has a next block is in
     *         the procedure searched, otherwise is the super index used.
     */
    @Override
    public Block getBlockAtIndex(int index, ExecutionStack systemStack)  {
        if (index < 0) {
            return null;
        }
        if (index == 0) {
            return this;
        }
        if (hasNext()) {
            setReturnToOfNext(systemStack);
            return procedure.getBlockAtIndex(index-1, systemStack);
        }
        return super.getBlockAtIndex(index, systemStack);
    }

    /**
     * Get the index of the given block.
     *
     * @param block The block to get the index of.
     * @param systemStack The stack to use in the index calculation.
     *
     * @return If this block has a next block, then is the default index used,
     *         otherwise is the super block searched.
     */
    @Override
    public int getIndexOfBlock(Block block, ExecutionStack systemStack)  {
        if (block == null) {
            return -1;
        }
        if (block == this) {
            return 0;
        }
        if (hasNext()) {
            setReturnToOfNext(systemStack);
            return 1 + procedure.getIndexOfBlock(block, systemStack);
        }
        return super.getIndexOfBlock(block,systemStack);
    }

    /**
     * Clone this block.
     *
     * @return A new call block with the same procedure as this block.
     *
     * @effect The clone is subscribed to this block.
     */
    @Override
    public Block clone() {
        ProcedureCall toReturn = new ProcedureCall(procedure);
        this.subscribe(toReturn);
        return toReturn;
    }

    /**
     * Notify that the procedure is deleted.
     *
     * @effect Call the event on procedure deleted for all subscribed listeners.
     */
    private void notifyProcedureDeleted() {
        for (CallListener listener : new ArrayList<>(listeners)) {
            listener.onProcedureDeleted();
        }
    }

    /**
     * Method to call if this block is deleted.
     *
     * @effect Terminates this block.
     * @effect Disconnect the mainconnector of this block (if any block is connected)
     * @effect Disconnect the block at the first sub connector (if any block is connected)
     */
    public void onProcedureDeleted() {
        this.terminate();
        if (getMainConnector().isConnected()) {
            getMainConnector().disconnect();
        }
        if (getSubConnectorAt(0).isConnected()) {
            getSubConnectorAt(0).getConnectedBlock().getMainConnector().disconnect();
        }
    }

    /**
     * Set the return to block of the next block
     *
     * @effect This call is connected throught the subconnector, then is the return to block of
     *         the procedure set to the connected block and the return to block of the connected
     *         block set to the return to block of this call.
     * @effect If this block is not connected to a block on the bottom and the procedure has a
     *         different return to block then this call, then is the return to block of the procedure
     *         set to the return to block of this call.
     */
    private void setReturnToOfNext(ExecutionStack systemStack) {
        if (getSubConnectorAt(0).isConnected()) {
            systemStack.push(getSubConnectorAt(0).getConnectedBlock());
        }
    }

    /**
     * Push the next blocks to the stack.
     *
     * @param stack The stack to push the blocks on.
     *
     * @effect First pushes the next blocks to the stack, and then push the
     *         procedure to the stack.
     */
    @Override
    public void pushNextBlocks(ExecutionStack stack) {
        super.pushNextBlocks(stack);
        stack.push(procedure);
    }
}
