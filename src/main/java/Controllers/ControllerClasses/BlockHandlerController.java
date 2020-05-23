package Controllers.ControllerClasses;

import Controllers.IGUISystemBlockLink;
import GUI.Blocks.IGUIBlock;
import System.BlockStructure.Blocks.Block;
import System.Logic.ProgramArea.PABlockHandler;

/**
 * Controller class to handle the blocks in the system.
 *
 * @invar A block handler controller must have a valid block link.
 *        | isValidIGUISystemBlockLink(blockLink)
 * @invar A block handler controller must have a valid pa block handler.
 *        | isValidPABlockHandler(blockHandler)
 *
 * @author Alpha-team
 */
public class BlockHandlerController {

    /**
     * Variable referring to the block link of this block handler controller.
     */
    private final IGUISystemBlockLink blockLink;
    /**
     * Variable referring to the block handler of the block handler controller.
     */
    private final PABlockHandler blockHandler;

    /**
     * Initialise a new block handler controller with given block database and pa block handler.
     *
     * @param blockLink The block link for this block handler controller.
     * @param blockHandler The pa block handler for this block handler controller.
     *
     * @post The database of this block handler controller is set to the given database.
     * @post The pa block handler of this block handler controller is set to the given pa block handler.
     *
     * @throws IllegalArgumentException
     *         When the given block link is not valid.
     * @throws IllegalArgumentException
     *         When the given pa block handler is not valid.
     */
    public BlockHandlerController(IGUISystemBlockLink blockLink, PABlockHandler blockHandler) throws IllegalArgumentException {
        if (!isValidIGUISystemBlockLink(blockLink)) {
            throw new IllegalArgumentException("The given block database is not valid!");
        }
        if (!isValidPABlockHandler(blockHandler)) {
            throw new IllegalArgumentException("The given block handler is not valid");
        }
        this.blockLink = blockLink;
        this.blockHandler = blockHandler;
    }

    /**
     * Checks whether or not the given block link is valid.
     *
     * @param blockLink The block link to check.
     *
     * @return True if and only if the given block link is effective.
     */
    public static boolean isValidIGUISystemBlockLink(IGUISystemBlockLink blockLink) {
        return blockLink != null;
    }

    /**
     * Checks whether or not the given pa block handler is valid.
     *
     * @param blockHandler The pa block handler to check.
     *
     * @return True if and only if the given pa block handler is effective.
     */
    public static boolean isValidPABlockHandler(PABlockHandler blockHandler) {
        return blockHandler != null;
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
        blockLink.addBlockPair(block, blockHandler.getFromPalette(index));
        Block toAdd = blockLink.getBlockFromGUIBlock(block);
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
        Block toDelete = blockLink.getBlockFromGUIBlock(block);
        blockHandler.deleteProgram(toDelete);
        blockLink.removeBlock(block);
    }

    /**
     * Add the given block to the program area as an existing block.
     *
     * @param block The block to add.
     *
     * @effect The compatibel system block is received and added to the program area.
     */
    public void addExistingBlockAsProgram(IGUIBlock block) {
        Block toAdd = blockLink.getBlockFromGUIBlock(block);
        blockHandler.addToPA(toAdd);
    }

    /**
     * Get the block to highlight.
     *
     * @return The gui block which should be highlighted, if no such block
     *         exists is null returned.
     */
    public IGUIBlock getHighlightedBlock() {
        Block highlightedBlock = blockHandler.getPA().getNextBlockInProgram();
        return (highlightedBlock != null) ? blockLink.getGUIBlockFromBlock(highlightedBlock) : null;
    }
}
