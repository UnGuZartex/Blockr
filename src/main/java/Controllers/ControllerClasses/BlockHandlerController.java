package Controllers.ControllerClasses;

import Controllers.BlockLinkDatabase;
import GUI.Blocks.IGUIBlock;
import System.BlockStructure.Blocks.Block;
import System.Logic.ProgramArea.PABlockHandler;

public class BlockHandlerController {

    private final BlockLinkDatabase blockDatabase;
    private final PABlockHandler blockHandler;

    public BlockHandlerController(BlockLinkDatabase blockDatabase, PABlockHandler blockHandler) {
        this.blockDatabase = blockDatabase;
        this.blockHandler = blockHandler;
    }

    public void addBlockToPA(IGUIBlock block, int index) {
        blockDatabase.addBlockPair(block, blockHandler.getFromPalette(index));
        Block toAdd = blockDatabase.getBlockFromGUIBlock(block);
        blockHandler.addToPA(toAdd);
    }

    public void deleteFromPA(IGUIBlock block) {
        Block toDelete = blockDatabase.getBlockFromGUIBlock(block);
        blockDatabase.removeBlock(block);
        blockHandler.deleteProgram(toDelete);
    }

    public IGUIBlock getHighlightedBlock() {
        Block highlightedBlock = blockHandler.getPA().getNextBlockInProgram();
        return (highlightedBlock != null) ? blockDatabase.getGUIBlockFromBlock(highlightedBlock) : null;
    }
}
