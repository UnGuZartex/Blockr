package Controllers.ControllerClasses;

import Controllers.IGUI_System_BlockLink;
import GUI.Blocks.IGUIBlock;
import System.BlockStructure.Blocks.Block;
import System.Logic.ProgramArea.PABlockHandler;

/**
 * Controller class to handle the blocks in the system.
 *
 * @invar A block handler controller must have an effective database link.
 *        | blockDatabase != null
 * @invar A block handler controller must have an effective pa block handler.
 *        | blockHandler != null
 *
 * @author Alpha-team
 */
public class BlockHandlerController {

    /**
     * Variable referring to the block data base of this block handler controller.
     */
    private final IGUI_System_BlockLink blockDatabase;
    /**
     * Variable referring to the block handler of the block handler controller.
     */
    private final PABlockHandler blockHandler;

    /**
     * Initialise a new block handler controller with given block database and pa block handler.
     *
     * @param blockDatabase The database for this block handler controller.
     * @param blockHandler The pa block handler for this block handler controller.
     *
     * @post The database of this block handler controller is set to the given database.
     * @post The pa block handler of this block handler controller is set to the given pa block handler.
     *
     * @throws IllegalArgumentException
     *         When the given database is not effective.
     * @throws IllegalArgumentException
     *         When the given pa block handler is not effective.
     */
    public BlockHandlerController(IGUI_System_BlockLink blockDatabase, PABlockHandler blockHandler) throws IllegalArgumentException {
        if (blockDatabase == null) {
            throw new IllegalArgumentException("The given block database is not effective!");
        }
        if (blockHandler == null) {
            throw new IllegalArgumentException("The given block handler is not effective");
        }
        this.blockDatabase = blockDatabase;
        this.blockHandler = blockHandler;
    }

    /**
     * Get a block with the given index and add it to the program area.
     *
     * @param block The gui block to connect.
     * @param index The index of the block in the palette.
     *
     * @effect The given block and a new block from the palette is added to the database.
     * @effect The new block from the palette is added to the program area.
     */
    public void addBlockToPA(IGUIBlock block, int index) {
        blockDatabase.addBlockPair(block, blockHandler.getFromPalette(index));
        Block toAdd = blockDatabase.getBlockFromGUIBlock(block);
        blockHandler.addToPA(toAdd);
    }

    /**
     * Delete the given block from the program area.
     *
     * @param block The gui block to delete.
     *
     * @effect The block which corresponds to the given gui block in the program
     *         area is deleted.
     */
    public void deleteFromPA(IGUIBlock block) {
        Block toDelete = blockDatabase.getBlockFromGUIBlock(block);
        blockDatabase.removeBlock(block);
        blockHandler.deleteProgram(toDelete);
        
    }

    /**
     * Get the block to highlight.
     *
     * @return The gui block which should be highlighted, if no such block
     *         exists is null returned.
     */
    public IGUIBlock getHighlightedBlock() {
        Block highlightedBlock = blockHandler.getPA().getNextBlockInProgram();
        return (highlightedBlock != null) ? blockDatabase.getGUIBlockFromBlock(highlightedBlock) : null;
    }
}
