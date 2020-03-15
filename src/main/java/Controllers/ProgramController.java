package Controllers;


import GUI.Blocks.GUIBlock;
import System.BlockStructure.Blocks.Block;
import System.GameWorld.Level.LevelLoader;
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.Program;


public class ProgramController {
    private final PABlockHandler blockHandler = new PABlockHandler();
    private final LevelLoader loader = new LevelLoader();
    private final GUItoSystemInterface converter = new GUItoSystemInterface(blockHandler);
    private final ConnectionController controller = new ConnectionController(converter, blockHandler);

    public void addBlockToPA(GUIBlock block) {
        Block toAdd = converter.getBlockFromGUIBlock(block);
        blockHandler.addToPA(toAdd);
        resetProgram();
    }

    public void deleteFromPA(GUIBlock block) {
        Block toDelete = converter.getBlockFromGUIBlock(block);
        converter.removeBlock(block);
        blockHandler.deleteProgram(toDelete);
        resetProgram();
    }

    public void deleteAsProgram(GUIBlock block) {
        Block toDelete = converter.getBlockFromGUIBlock(block);
        blockHandler.deleteProgram(toDelete);
    }

    public boolean reachedMaxBlocks() {
        return blockHandler.hasReachedMaxBlocks();
    }

    public GUIBlock getHightlightedBlock() {
        Program program = blockHandler.getPA().getProgram();
        if (program != null) {
            return converter.getGUIBlockFromBlock(program.getCurrentBlock());
        }

        return null;
    }

    public GUIBlock getBlock(String ID, int x, int y) {
        if (!reachedMaxBlocks()) {
            return converter.createNewGUIBlock(ID, x, y);
        }
        return null;
    }

    public void resetProgram() {
        Program program = blockHandler.getPA().getProgram();
        if (program != null) {
            program.resetProgram();
        }

        loader.resetLevel();
    }

    public void runProgramStep() {
        Program program = blockHandler.getPA().getProgram();
        if (program != null) {
            program.executeStep();
        }
    }

    public ConnectionController getController() {
        return controller;
    }
}
