package Controllers;


import GUI.Blocks.GUIBlock;
import System.GameWorld.Level.LevelLoader;
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.Program;


public class ProgramController {
    private final PABlockHandler blockHandler = new PABlockHandler();
    private final LevelLoader loader = new LevelLoader();
    private final GUItoSystemInterface converter = new GUItoSystemInterface(blockHandler);
    private final ConnectionController controller = new ConnectionController(converter);


//    public void addBlockToPA(GUIBlock block) {
//        Block toAdd = converter.convert(block.getID());
//        blockHandler.addToPA(toAdd);
//        loadLevel();
//    }
//
//    public void connectToSubConnector(GUIBlock block, GUIConnector connector) {
//        Block toAdd = converter.convert(block.getID());
//        blockHandler.addToPA(toAdd);
//        loadLevel();
//    }


    public boolean reachedMaxBlocks() {
        return blockHandler.hasReachedMaxBlocks();
    }


    public GUIBlock getBlock(String ID, int x, int y) {
        return converter.createNewGUIBlock(ID, x, y);
    }

    public void resetProgram() {
        Program program = blockHandler.getPA().getProgram();
        if (program != null) {
            program.resetProgram();
        }
        loadLevel();

    }

    public void runProgramStep() {
        Program program = blockHandler.getPA().getProgram();
        if (program != null) {
            program.executeStep();
        }
    }

    public void loadLevel() {
        loader.loadLevel();
    }


    public ConnectionController getController() {
        return controller;
    }
}
