package Controllers;


import System.GameWorld.Level.LevelLoader;
import System.Logic.Converter;
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.Program;


public class ProgramController {
    private final PABlockHandler blockHandler = new PABlockHandler();
    private final Converter converter = new Converter();
    private LevelLoader loader = new LevelLoader();


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
        return blockHandler.getMaxReached();
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



}
