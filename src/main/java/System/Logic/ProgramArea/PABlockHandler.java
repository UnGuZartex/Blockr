package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.SubConnector;
import System.Logic.Palette.Palette;

import java.util.List;

/**
 * A class to handle the blocks in the program area.
 *
 * @invar The PA Block Handler must have a valid program area.
 *        | isValidProgramArea(programArea)
 * @invar There must be a proper number of blocks in the program area.
 *        | hasProperNbBlocks()
 *
 * @author Alpha-team
 */
public class PABlockHandler {

    /**
     * Variable referring to the palette state in this program area block handler.
     */
    private final Palette palette;

    /**
     * Variable referring to the program area in this program area block handler.
     */
    private final ProgramArea programArea;

    /**
     * Variable referring to the connection handler in this program area block handler.
     */
    private final ConnectionHandler connectionHandler = new ConnectionHandler();

    /**
     * Variable referring to the max amount of blocks that may be used.
     */
    private int maxBlocks = 10;

    /**
     * Create a new program area block handler with a given list of starting palette blocks.
     *
     * @param paletteBlocks The given list of starting palette blocks.
     * @param programArea The program area for the program area block handler.
     *
     * @post The program area of this pa block handler is set to the given program area.
     *
     * @effect A new palette is initialized with the given list of blocks.
     *
     * @throws IllegalArgumentException
     *         If the given program area is invalid.
     */
    public PABlockHandler(List<Block> paletteBlocks, ProgramArea programArea) throws IllegalArgumentException {
        if (!isValidProgramArea(programArea)) {
            throw  new IllegalArgumentException("The given program area is not valid!");
        }
        palette = new Palette(paletteBlocks);
        this.programArea = programArea;
    }

    /**
     * Checks whether the given program area is valid or not.
     *
     * @param programArea The program area to check.
     *
     * @return True if and only if the given program area is effective.
     */
    public static boolean isValidProgramArea(ProgramArea programArea) {
        return programArea != null;
    }

    /**
     * Get the palette from this block handler.
     *
     * @return The palette from this block handler.
     */
    public Palette getPalette() {
        return this.palette;
    }

    /**
     * Get the program area of this program area block handler.
     *
     * @return The program area of this program area block handler.
     */
    public ProgramArea getPA() {
        return programArea;
    }

    /**
     * Checks whether the maximum number of blocks has been reached.
     *
     * @return True if and only if the amount of blocks is smaller or equal
     *         to the maximum number of blocks.
     */
    public boolean hasProperNbBlocks() {
        return programArea.getAllBlocksCount() <= maxBlocks;
    }

    /**
     * Get the block at the given index from the palette.
     *
     * @param index The index of the wanted block in the palette.
     *
     * @return A block from the palette at the given index if the max
     *         number of blocks has not been reached yet.
     */
    public Block getFromPalette(int index) {
        return palette.getNewBlock(index);
    }

    /**
     * Add the given block to the program area and update if the
     * max number has not been reached yet.
     *
     * @param block The block to add to the program area.
     *
     * @effect Program reset command is added to the program area.
     * @effect The given block is added to the program area.
     * @effect Palette is updated.
     *
     * @throws IllegalStateException
     *         When max blocks has been reached.
     */
    public void addToPA(Block block) throws IllegalStateException {
        if (hasReachedMaxBlocks()) {
            throw new IllegalStateException("Max blocks has been reached. " +
                    "This block can't be added to the program area anymore.");
        }

        programArea.addProgramResetCommand();
        programArea.addProgram(block);
        updatePalette();
    }

    /**
     * Connect a block to a sub connector if it is already in the program area
     * and update the amount of blocks.
     *
     * @param block The block to connect.
     * @param subConnector The sub connector of the block to connect to.
     *
     * @pre The given sub connector should be from a block which is in the program area..
     * @pre The given sub connector should be able to connect to the main connector of the given block.
     *
     * @effect Program reset command is added to the program area.
     * @effect The given block is deleted as a program in the program area.
     * @effect The two blocks are connected.
     * @effect The root block of the newly connected block structure is added as a program,
     *         if it isn't already.
     * @effect Palette is updated.
     */
    public void connectToExistingBlock(Block block, SubConnector subConnector) {
        programArea.addProgramResetCommand();
        programArea.deleteProgram(block);
        connectionHandler.connect(block, subConnector);
        programArea.addHighestAsProgram(block);
        updatePalette();
    }

    /**
     * Disconnect the given block in the program area and update the
     * amount of blocks.
     *
     * @param block The block to disconnect.
     *
     * @pre The given block must be in the program area.
     *
     * @effect Program reset command is added to the program area.
     * @effect The given block is disconnected on its main connector.
     * @effect The given block is added as a program.
     * @effect Palette is updated.
     */
    public void disconnectInPA(Block block) {
        programArea.addProgramResetCommand();
        connectionHandler.disconnect(block);
        programArea.addProgram(block);
        updatePalette();
    }

    /**
     * Delete a program from the program area with given start block.
     *
     * @param block The starting block of the program to delete.
     *
     * @effect Program reset command is added to the program area.
     * @effect The given block is disconnected on its main connector.
     * @effect The given block is deleted from the program area.
     * @effect The given block is terminated.
     * @effect Palette is updated.
     */
    public void deleteProgram(Block block) {
        programArea.addProgramResetCommand();
        connectionHandler.disconnect(block);
        programArea.deleteProgram(block);
        block.terminate();
        updatePalette();
    }

    /**
     * Checks whether the max number of blocks has been reached.
     *
     * @return True if and only if the amount of blocks in the program area
     *         is greater than or equal to the maximum amount of blocks in the game state.
     */
    public boolean hasReachedMaxBlocks() {
        return programArea.getAllBlocksCount() >= maxBlocks;
    }

    /**
     * Sets the max number of blocks of this PAHandler
     * @param maxBlocks the amount of blocks to set
     */
    protected void setMaxBlocks(int maxBlocks) {
        this.maxBlocks = maxBlocks;
    }

    /**
     * Update whether the palette can create more blocks.
     */
    private void updatePalette() {
        palette.update(hasReachedMaxBlocks());
    }
}