package Controllers.ControllerClasses;


import Controllers.blockLinkDatabase;
import Controllers.ProgramListener;
import GUI.Blocks.GUIBlock;
import GUI.Blocks.IGUIBlock;
import GameWorldAPI.GameWorld.GameWorld;
import System.BlockStructure.Blocks.Block;
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.Program;


public class ProgramController {

    private final GameWorld gameWorld;
    private final blockLinkDatabase blockDatabase;
    private final PABlockHandler blockHandler;

    public ProgramController(blockLinkDatabase blockDatabase, PABlockHandler blockHandler, GameWorld gameWorld) {
        this.blockDatabase = blockDatabase;
        this.blockHandler = blockHandler;
        this.gameWorld = gameWorld;
    }

    public void addBlockToPA(IGUIBlock block, int index) {
        blockDatabase.addBlockPair(block, blockHandler.getFromPalette(index));
        Block toAdd = blockDatabase.getBlockFromGUIBlock(block);
        blockHandler.addToPA(toAdd);
        resetGameWorld();
    }

    public void deleteFromPA(IGUIBlock block) {
        Block toDelete = blockDatabase.getBlockFromGUIBlock(block);
        blockDatabase.removeBlock(block);
        blockHandler.deleteProgram(toDelete);
        resetGameWorld();
    }

    public IGUIBlock getHightlightedBlock() {
        Program program = blockHandler.getPA().getProgram();
        if (program != null) {
            return blockDatabase.getGUIBlockFromBlock(program.getCurrentBlock());
        }

        return null;
    }

    public void resetGameWorld() {
        gameWorld.reset();
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
}
