package System.Logic.ProgramArea;

import GameWorldAPI.GameWorld.Result;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.SubConnector;

import java.util.AbstractMap;
import java.util.Map;

/**
 * A class for a program to execute. A program only has a starting
 * block and execution goes through the program as a linked list
 * using the connectors of the blocks in this program.
 *
 * @author Alpha-team
 */
public class Program {

    /**
     * Variable referring to the start block of this program. This is
     * the first block of the program.
     */
    private Block startBlock;
    /**
     * Variable referring to the block which should be executed next.
     */
    private Block currentBlock;
    /**
     * Variable referring to the result of the last executed step in the program.
     */
    private Result lastResult = Result.SUCCESS;



    private CommandHistory history = new CommandHistory();

    /**
     * Initialise a new program with given start block and reset the program.
     *
     * @param start The start block for this program.
     *
     * @effect The program is reset and thus ready for execution.
     *
     * @post The start block of this program is set to the given block.
     */
    public Program(Block start) {
        startBlock = start;
        currentBlock = startBlock;
    }

    /**
     * Execute a step of this program, set the current block
     * to the next block to execute if it is not finished yet and
     * return the current result of the executed program step.
     *
     * @effect The functionality of the current block is evaluated.
     *
     * @post The current block of this program is set to the next block.
     *
     * @return The current result of the program
     *
     * @throws IllegalStateException
     *         If this program is not valid.
     */
    public Result executeStep() {
        if (!isValidProgram()) {
            throw new IllegalStateException("This program is invalid!");
        }

        if (!isFinished()) {
            lastResult = history.execute(currentBlock, lastResult);
            currentBlock = currentBlock.getNext();
        }
        return lastResult;
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
     * Reset this program. The blocks in this program are reset,
     * and the current block is set to the start block.
     *
     * @post The current block is set to the start block.
     * @post the start block is reset.
     */
    public void resetProgram() {
        Map.Entry<Result, Block> reset = history.reset();
        this.currentBlock = startBlock;
        this.lastResult = reset.getKey();
    }

    public Result undoProgram() {
        Map.Entry<Result, Block> undo = history.undo(lastResult);
        if (!undo.equals(new AbstractMap.SimpleEntry<>(null,null))) {
            this.currentBlock = undo.getValue();
            this.lastResult = undo.getKey();
        }
        return this.lastResult;
    }

    public Result redoProgram() {
        Map.Entry<Result, Block> redo = history.redo(lastResult);
        if (!redo.equals(new AbstractMap.SimpleEntry<>(null,null))) {
            this.currentBlock = redo.getValue();
            this.lastResult = redo.getKey();
        }
        return this.lastResult;
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
     * Get the size of the given block.
     *
     * @param block The block to compute the size of.
     *
     * @return The size of the given block. This is the number of blocks
     *         which are connected through a sub connector on this block.
     */
    protected static int getSizeOfBlock(Block block) {
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

    public boolean isExecuting() {
        return !(startBlock == currentBlock);
    }
}
