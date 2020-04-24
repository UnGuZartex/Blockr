package System.Logic.ProgramArea;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.History.Snapshot;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.SubConnector;

import java.time.LocalDateTime;

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
     * Variable referring to the start block of this program. This is
     * the first block of the program.
     */
    private final Block startBlock;
    /**
     * Variable referring to the block which should be executed next.
     */
    private Block currentBlock;
    /**
     * Variable referring to the default result of a program.
     */
    public final static Result DEFAULT_RESULT = Result.SUCCESS;
    /**
     * Variable referring to the result of the last executed step in the program.
     */
    private Result lastResult = DEFAULT_RESULT;

    /**
     * Initialise a new program with given start block and reset the program.
     *
     * @param start The start block for this program.
     *
     * @post The start block of this program is set to the given block.
     * @post The current block of this program is set to the given block.
     *
     * @throws IllegalArgumentException
     *         If the given block is no valid start block.
     */
    public Program(Block start) throws IllegalArgumentException{
        if (!isValidStartBlock(start)) {
            throw new IllegalArgumentException("The given start block is not valid!");
        }
        startBlock = start;
        currentBlock = start;
    }

    /**
     * Checks if the given block is a valid starting block.
     *
     * @param block The block to check.
     *
     * @return True if and only if the given block is effective.
     */
    public static boolean isValidStartBlock(Block block) {
        return block != null;
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
        return currentBlock;
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
     * @effect The functionality of the current block is evaluated.
     *
     * @post The current block of this program is set to the next block
     *       if the program is not finished yet.
     *
     * @return The result of an execution if the program is not finished,
     *         otherwise is the last result returned
     *
     * @throws IllegalStateException
     *         If this program is not valid.
     */
    public void executeStep(GameWorld gameWorld) throws IllegalStateException{
        if (!isValidProgram()) {
            throw new IllegalStateException("This program is not a valid program to execute!");
        }
        if (!isFinished()) {
            lastResult = currentBlock.getFunctionality().evaluate(gameWorld);
            currentBlock = currentBlock.getNext();
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
     * Checks whether this program is finished or not.
     *
     * @return True if and only if the program has finished executing all the blocks
     *         or if the last result of executing the program is not a SUCCESS.
     */
    public boolean isFinished() {
        return currentBlock == null || lastResult != Result.SUCCESS;
    }

    /**
     * Get the size of this program.
     *
     * @return The size of this program, which is the size of the start
     *         block of this program
     *         | getSizeOfBlock(startBlock)
     */
    public int getSize() {
        return getSizeOfBlock(startBlock);
    }

    /**
     * Reset this program.
     *
     * @post The current block is set to the start block.
     * @post The last result is set to the default result.
     */
    public void resetProgram() {
        this.currentBlock = startBlock;
        this.lastResult = DEFAULT_RESULT;
    }

    public boolean isInStartState() {
        return currentBlock == startBlock && lastResult == Result.SUCCESS;
    }

    /**
     * Get the size of the given block.
     *
     * @param block The block to compute the size of.
     *
     * @return The size of the given block. This is the number of blocks
     *         which are connected through a sub connector on this block.
     */
    private static int getSizeOfBlock(Block block) {
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

    public Snapshot createSnapshot() {
        return new ProgramSnapshot();
    }

    public void loadSnapshot(Snapshot snapshot) {
        ProgramSnapshot programSnapshot = (ProgramSnapshot) snapshot;
        currentBlock = startBlock.getBlockAtIndex(programSnapshot.currentBlockIndex);
        lastResult = programSnapshot.currentResult;
    }

    private class ProgramSnapshot implements Snapshot {
        private final int currentBlockIndex;
        private final Result currentResult = lastResult;

        public ProgramSnapshot() {
            System.out.println("index: " + currentBlock.getIndexOfBlock(currentBlock));
            currentBlockIndex = startBlock.getIndexOfBlock(currentBlock);
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public LocalDateTime getSnapshotDate() {
            return null;
        }
    }
}