package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.SubConnector;
import System.Logic.Palette.Palette;

import java.util.List;

/**
 * A class to handle the blocks in the program area.
 *
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
    private final ProgramArea PA = new ProgramArea();
    /**
     * Variable referring to the connection handler in this program area block handler.
     */
    private final ConnectionHandler connectionHandler = new ConnectionHandler();
    /**
     * Variable referring to the amount of blocks used in this program area block handler.
     */
    private int amountOfBlocks = 0;

    /**
     * Variable referring to the max amount of blocks that may be used.
     */
    private int maxBlocks = 10;

    /**
     * Create a new program area block handler with a given list of starting palette blocks.
     *
     * @param paletteBlocks The given list of starting palette blocks.
     *
     * @effect A new palette is initialized with the given list of blocks.
     */
    public PABlockHandler(List<Block> paletteBlocks) {
        palette = new Palette(paletteBlocks);
    }

    /**
     * Checks whether the maximum number of blocks has been reached.
     *
     * @return True if and only if the amount of blocks is smaller or equal
     *         to the maximum number of blocks.
     */
    public boolean hasProperNbBlocks() {
        return amountOfBlocks <= maxBlocks;
    }

    /**
     * Get the block at the given index from the palette.
     *
     * @param index The index of the wanted block in the palette.
     *
     * @return A block from the palette at the given index if the max
     *         number of blocks has not been reached yet, otherwise
     *         null is returned.
     */
    public Block getFromPalette(int index) {
        if (!hasReachedMaxBlocks()) {
            return palette.getNewBlock(index);
        }

        return null;
    }

    /**
     * Add the given block to the program area and update if the
     * max number has not been reached yet.
     *
     * @post The given block is added to the program area if the
     *       maximum number of blocks hasn't been reached.
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
     *
     * @effect The given block is deleted as a program in the program area.
     * @effect The two blocks are connected.
     * @effect The root block of the newly connected block structure is added as a program,
     *         if it isn't already.
     */
    public void connectToExistingBlock(Block block, SubConnector subConnector) {
        PA.deleteProgram(block);
        connectionHandler.connect(block, subConnector);
        PA.addHighestAsProgram(block);
        Update();
    }

    /**
     * Disconnect the given block in the program area and update the
     * amount of blocks.
     *
     * @param block The block to disconnect.
     *
     * @effect The given block is disconnected on its main connector.
     * @effect The given block is added as a program.
     */
    public void disconnectInPA(Block block) {
        connectionHandler.disconnect(block);
        PA.addProgram(block);
        Update();
    }

    /**
     * Delete a program from the program area with given start block.
     *
     * @param block The starting block of the program to delete.
     *
     * @effect The given block is disconnected on its main connector.
     * @effect The given block is deleted from the program area.
     */
    public void deleteProgram(Block block) {
        connectionHandler.disconnect(block);
        PA.deleteProgram(block);
        Update();
    }

    /**
     * Update the amount of blocks used in the program area and
     * reset the current program(s) in the program area.
     *
     * @post The total number of blocks has been updated.
     *
     * @effect The program(s) in the program area are reset.
     */
    private void Update() {
        amountOfBlocks = PA.getAllBlocksCount();
        PA.resetProgram();
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
     * Checks whether the max number of blocks has
     * been reached.
     *
     * @return True if and only if the amount of blocks is greater than or
     *         equal to the maximum amount of blocks in the game state.
     */
    private boolean hasReachedMaxBlocks() {
        return amountOfBlocks >= maxBlocks;
    }
}
