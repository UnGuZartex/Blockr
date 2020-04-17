package Controllers.ControllerClasses;

import Controllers.BlockLinkDatabase;
import Controllers.ProgramListener;
import GUI.Blocks.IGUIBlock;
import GameWorldAPI.GameWorld.GameWorld;
import System.BlockStructure.Blocks.Block;
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.Program;


public class ProgramController {

    private final GameWorld gameWorld;
    private final BlockLinkDatabase blockDatabase;
    private final PABlockHandler blockHandler;

    public ProgramController(BlockLinkDatabase blockDatabase, PABlockHandler blockHandler, GameWorld gameWorld) {
        this.blockDatabase = blockDatabase;
        this.blockHandler = blockHandler;
        this.gameWorld = gameWorld;
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

    public IGUIBlock getHightlightedBlock() {
        Program program = blockHandler.getPA().getProgram();
        if (program != null) {
            Block hightlightedBlock = program.getCurrentBlock();
            if (hightlightedBlock != null) {
                return blockDatabase.getGUIBlockFromBlock(hightlightedBlock);
            }
        }

        return null;
    }

    public void undoProgramStep() {
        blockHandler.getPA().undoProgram();
    }

    public void redoProgramStep() {
        blockHandler.getPA().redoProgram();
    }

    public void runProgramStep() {
        blockHandler.getPA().runProgramStep();
    }

    public boolean isExecuted() {
        return blockHandler.getPA().isExecuted();
    }

    public void subscribeListener(ProgramListener listener) {
        blockHandler.getPA().subscribe(listener);
    }

    public void unsubscribeListener(ProgramListener listener) {
        blockHandler.getPA().unsubscribe(listener);
    }

    public void resetProgram() {
        blockHandler.getPA().resetProgram();
    }
}
