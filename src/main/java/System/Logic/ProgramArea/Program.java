package System.Logic.ProgramArea;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.Utility.Snapshot;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.SubConnector;

import java.time.LocalDateTime;
import java.util.Stack;

/**
 * A class for a program to execute. A program only has a starting
 * block and execution goes through the program as a linked list
 * using the connectors of the blocks in this program.
 *
 * @invar Each program must have a valid start block.
 *        | isValidStartBlock(startBlock)
 *
 * @author Alpha-team
 */
public class Program {

    /**
     * Variable referring to the default result of a program.
     */
    public final static Result DEFAULT_RESULT = Result.SUCCESS;

    /**
     * Variable referring to the start block of this program. This is
     * the first block of the program.
     */
    private final Block startBlock;

    /**
     * Variable referring to the result of the last executed step in the program.
     */
    private Result lastResult = DEFAULT_RESULT;

    /**
     * Variable referring to the execution state of the program.
     */
    private boolean isResettable = false;

    /**
     * Variable referring to the execution stack.
     */
    private ExecutionStack executionStack = new ExecutionStack();

    /**
     * Initialise a new program with given start block and reset the program.
     *
     * @param start The start block for this program.
     *
     * @post The start block of this program is set to the given block.
     *
     * @effect The start block is pushed to the execution stack.
     *
     * @throws IllegalArgumentException
     *         If the given block is no valid start block.
     */
    public Program(Block start) throws IllegalArgumentException{
        if (!isValidStartBlock(start)) {
            throw new IllegalArgumentException("The given start block is not valid!");
        }
        startBlock = start;
        executionStack.push(start);
    }

    /**
     * Checks if the given block is a valid starting block.
     *
     * @param block The block to check.
     *
     * @return True if and only if the given block is effective.
     */
    public static boolean isValidStartBlock(Block block) {
        return block != null && !block.isConnectedOnMain();
    }

    /**
     * Get the start block of this program.
     *
     * @return The start block of this program.
     */
    public Block getStartBlock() {
        return startBlock;
    }

    /**
     * Get the block which has to be executed currently.
     *
     * @return The block which should be executed currently.
     */
    public Block getCurrentBlock() {
        return executionStack.peek();
    }

    /**
     * Get the last result of this program.
     *
     * @return The last result.
     */
    public Result getLastResult() {
        return lastResult;
    }

    /**
     * Execute a step of this program.
     *
     * @param gameWorld The game world to operate on.
     *
     * @effect If the program isn't finished, then the program is resettable.
     * @effect If the program isn't finished yet, then is the next block popped from
     *         the execution stack, it's functionality evaluated and the next blocks
     *         pushed on the stack.
     *
     * @throws IllegalStateException
     *         If this program is not valid.
     */
    public void executeStep(GameWorld gameWorld) throws IllegalStateException{
        if (!isValidProgram()) {
            throw new IllegalStateException("This program is not a valid program to execute!");
        }
        if (!isFinished()) {
            isResettable = true;
            Block currentBlock = executionStack.pop();
            lastResult = currentBlock.getFunctionality().evaluate(gameWorld);
            currentBlock.pushNextBlocks(executionStack);
        }
    }

    /**
     * Checks whether this is a valid program.
     *
     * @return True if and only if the start block of this program
     *         has proper connections.
     */
    public boolean isValidProgram() {
        return startBlock.hasProperConnections();
    }

    /**
     * Checks whether this program is resettable.
     *
     * @return True if the program has executed a step before,
     *         false otherwise.
     */
    public boolean isResettable() {
        return isResettable;
    }

    /**
     * Checks whether this program is finished or not.
     *
     * @return True if and only if the program has finished executing all the blocks
     *         or if the last result of executing the program is not a SUCCESS.
     */
    public boolean isFinished() {
        return executionStack.isEmpty() || lastResult != Result.SUCCESS;
    }

    /**
     * Get the size of this program.
     *
     * @return The total number of blocks that this program contains.
     */
    public int getSize() {
        return getSizeOfBlock(startBlock);
    }

    /**
     * Reset this program.
     *
     * @post The execution stack only contains the start block.
     * @post The last result is set to the default result.
     * @post The executing variable is changed indicating the
     *       program is not executing anymore.
     */
    public void resetProgram() {
        executionStack.clear();
        executionStack.push(startBlock);
        this.lastResult = DEFAULT_RESULT;
        isResettable = false;
    }

    /**
     * Get the size of the given block.
     *
     * @param block The block to compute the size of.
     *
     * @return The size of the given block. This is the number of blocks
     *         which are connected through a sub connector on this block.
     */
    public static int getSizeOfBlock(Block block) {
        int sizeOfSubConnectList = block.getNbSubConnectors();
        int sum = 1;
        for (int i = 0; i < sizeOfSubConnectList; i++) {
            SubConnector newSubConnector = block.getSubConnectorAt(i);
            if (newSubConnector.isConnected()) {
                sum += getSizeOfBlock(newSubConnector.getConnectedBlock());
            }
        }
        return sum;
    }

    /**
     * Create a snapshot of this program.
     *
     * @return A new program snapshot.
     */
    public Snapshot createSnapshot() {
        return new ProgramSnapshot();
    }

    /**
     * Load the given snapshot.
     *
     * @param snapshot The snapshot to load.
     *
     * @post The execution stack is set to the correct execution stack.
     * @post The last result is set to the result stored in the snapshot.
     * @post The execution state is set to the state stored in the snapshot.
     */
    public void loadSnapshot(Snapshot snapshot) {
        ProgramSnapshot programSnapshot = (ProgramSnapshot) snapshot;
        executionStack = programSnapshot.getBlockStack(startBlock);
        lastResult = programSnapshot.currentResult;
        isResettable = programSnapshot.isExecutingNow;
    }

    /**
     * A private inner class for program snapshots.
     */
    private class ProgramSnapshot implements Snapshot {

        /**
         * Variable referring to a stack with the indexes of the exectuion stack.
         */
        private final Stack<Integer> executionStackCopy = getIndexStack(startBlock);

        /**
         * Variable referring to the result to remember.
         */
        private final Result currentResult = lastResult;

        /**
         * Variable referring to the execution state to remember.
         */
        private final boolean isExecutingNow = isResettable;

        /**
         * Variable referring to the creation time of this snapshot.
         */
        private final LocalDateTime creationTime = LocalDateTime.now();

        /**
         * Get the name of this snapshot.
         *
         * @return the name of this snapshot.
         */
        @Override
        public String getName() {
            return "Program snapshot " + this;
        }

        /**
         * Get the snapshot date.
         *
         * @return The the time this snapshot was initialised.
         */
        @Override
        public LocalDateTime getSnapshotDate() {
            return creationTime;
        }

        /**
         * Get the index stack from the given given starting point.
         *
         * @param startingPoint The block to compute the indexes from.
         *
         * @return An index stack with the index of the blocks in the execution stack
         *         starting from the given block.
         */
        public Stack<Integer> getIndexStack(Block startingPoint) {
            ExecutionStack toConvert = new ExecutionStack();
            toConvert.addAll(executionStack);
            Stack<Integer> indexStack = new Stack<>();
            while (!toConvert.isEmpty()) {
                indexStack.push(startingPoint.getIndexOfBlock(toConvert.pop(), new ExecutionStack()));
            }
            return indexStack;
        }

        /**
         * Convert the index stack into a block stack with indexes from the given block.
         *
         * @param startingPoint The block to compute the indexes from.
         *
         * @return An execution stack of which all blocks are at the index of the index stack, starting
         *         from the given block.
         */
        public ExecutionStack getBlockStack(Block startingPoint) {
            Stack<Integer> toConvert = new Stack<>();
            toConvert.addAll(executionStackCopy);
            ExecutionStack blockStack = new ExecutionStack();
            while (!toConvert.isEmpty()) {
                blockStack.push(startingPoint.getBlockAtIndex(toConvert.pop(), new ExecutionStack()));
            }
            return blockStack;
        }
    }
}