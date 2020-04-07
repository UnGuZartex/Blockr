package System.Logic.ProgramArea;

import GameWorldAPI.GameWorld.GameWorld;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Connectors.SubConnector;
import System.GameState.GameState;

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
     * Initialise a new program with given start block and reset the program.
     *
     * @param start The start block for this program.
     *
     * @effect The program is reset and thus ready for execution.
     *
     * @post The startblock of this program is set to the given block.
     */
    public Program(Block start) {
        startBlock = start;
        resetProgram();
    }

    /**
     * Execute a step of this program and set the current block
     * to the next block to execute if it is not finished yet.
     *
     * @effect The functionality of the current block is evaluated.
     *
     * @post The currentblock of this program is set to the next block.
     *
     * @throws IllegalStateException
     *         If this program is not valid.
     * @throws IllegalStateException
     *         If there is no level loaded.
     */
    public void executeStep() {

        if (!isValidProgram()) {
            throw new IllegalStateException("This program is invalid!");
        }

        if (GameState.getCurrentLevel() == null) {
            throw new IllegalStateException("There is no level loaded!");
        }

        if (!isFinished()) {
            //TODO GAMEWORLD HIER ERGENS MEEGEVEN
            currentBlock.getFunctionality().evaluate();
            currentBlock = currentBlock.getNext();
        }
    }

    /**
     * Checks whether or not this program is finished or not.
     *
     * @return True if and only if this program is finished, thus when
     *         the current block is again the start block and this block
     *         has already run.
     */
    public boolean isFinished() {
        return currentBlock == null;
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
     * Reset this program. The blocks in this program are reset
     * and the current block is set to the start block.
     *
     * @post The current block is set to the start block.
     * @post the start block is reset.
     */
    public void resetProgram() {
        currentBlock = startBlock;
        startBlock.reset();
    }

    /**
     * Checks whether or not this is a valid program.
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
}
