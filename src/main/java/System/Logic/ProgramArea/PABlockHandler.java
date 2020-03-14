package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.SubConnector;
import System.GameState.GameState;
import System.Logic.Palette.PaletteState;

/**
 * A class to handle the blocks in the program area.
 *
 * @author Alpha-team
 */
public class PABlockHandler {

    /**
     * Variable referring to the palette state in this program area block handler.
     */
    private PaletteState palette = new PaletteState();
    /**
     * Variable referring to the program area in this program area block handler.
     */
    private ProgramArea PA = new ProgramArea();
    /**
     * Variable referring to the connection handler in this program area block handler.
     */
    private ConnectionHandler connectionHandler = new ConnectionHandler();
    /**
     * Variable referring to the amount of blocks used in this program area block handler.
     */
    private int amountOfBlocks = 0;

    /**
     * Get the block with given ID from the palette.
     *
     * @param ID The ID of the block to get.
     *
     * @return A block from the palette with given ID if the max
     *         number of blocks has not been reached yet, otherwise
     *         is null returned.
     */
    public Block getFromPalette(String ID) {
        if (!hasReachedMaxBlocks()) {
            return palette.getNewBlockWithID(ID);
        } else {
            return null;
        }
    }

    /**
     * Add the given block to the program area and update if the
     * max number has not been reached yet.
     *
     * @param block The block to add to the program area.
     */
    public void addToPA(Block block) {
        if (!hasReachedMaxBlocks()) {
            PA.addProgram(block);
            Update();
        }
    }

    /**
     * Connect a block to a sub connector if it is already in the program area
     * and update the amount of blocks.
     *
     * @param block The block to connect.
     * @param subConnector The sub connector of the block to connect to.
     *
     * @pre The given sub connector should be from a block which is in the program area.
     */
    public void connectToExistingBlock(Block block, SubConnector subConnector) {
        connectionHandler.connect(block, subConnector);
        Update();
    }

    /**
     * Disconnect the given block in the program area and update the
     * amount of blocks.
     *
     * @param block The block to disconnect.
     *
     * @pre The given block should be connected to a program in the
     *      program area of this program area block handler.
     * @pre The given block may not be the starting block of any program
     *      in the program area of this program area block handler.
     */
    public void disconnectInPA(Block block) {
        connectionHandler.disconnect(block);
        Update();
    }

    /**
     * Delete a program from the program area with given start block.
     *
     * @param block The starting block of the program to delete.
     */
    public void deleteProgram(Block block) {
        PA.deleteProgram(block);
        Update();
    }

    /**
     * Update the amount of blocks used in the program area.
     */
    private void Update() {
        amountOfBlocks = PA.getAllBlocksCount();
    }

    /**
     * Get the program area of this program area block handler.
     *
     * @return The program area of this program area block handler.
     */
    public ProgramArea getPA() {
        return PA;
    }

    /**
     * Checks whether or not the max number of blocks has
     * been reached.
     *
     * @return True if and only if the amount of blocks is greater than or
     *         equal to the maximum amount of blocks in the game state.
     */
    public boolean hasReachedMaxBlocks() {
        return amountOfBlocks >= GameState.getMaxAmountOfBlocks();
    }
}
