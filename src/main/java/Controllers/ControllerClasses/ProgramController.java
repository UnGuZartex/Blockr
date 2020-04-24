package Controllers.ControllerClasses;

import Controllers.BlockLinkDatabase;
import Controllers.ProgramListener;
import GUI.Blocks.IGUIBlock;
import System.BlockStructure.Blocks.Block;
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.Program;


public class ProgramController {

    private final BlockLinkDatabase blockDatabase;
    private final PABlockHandler blockHandler;

    public ProgramController(BlockLinkDatabase blockDatabase, PABlockHandler blockHandler) {
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
        Program program = blockHandler.getPA().getProgram();
        if (program != null) {
            Block highlightedBlock = program.getCurrentBlock();
            if (highlightedBlock != null) {
                return blockDatabase.getGUIBlockFromBlock(highlightedBlock);
            }
        }

        return null;
    }

    public void runProgramStep() {
        blockHandler.getPA().runProgramStep();
    }

    public void subscribeListener(ProgramListener listener) {
        blockHandler.getPA().subscribe(listener);
    }

    public void unsubscribeListener(ProgramListener listener) {
        blockHandler.getPA().unsubscribe(listener);
    }

    public void resetProgram(boolean command) {
        blockHandler.getPA().resetProgram(command);
    }
}
