package Controllers.ControllerClasses;


import Controllers.GUItoSystemInterface;
import Controllers.ProgramListener;
import GUI.Blocks.GUIBlock;
import GameWorldAPI.GameWorld.GameWorld;
import System.BlockStructure.Blocks.Block;
import System.GameWorld.Level.LevelLoader;
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.Program;


public class ProgramController {

    private final GameWorld gameWorld;
    private final GUItoSystemInterface converter;
    private final PABlockHandler blockHandler;

    public ProgramController(GUItoSystemInterface converter, PABlockHandler blockHandler, GameWorld gameWorld) {
        this.converter = converter;
        this.blockHandler = blockHandler;
        this.gameWorld = gameWorld;
    }

    public void addBlockToPA(GUIBlock block) {
        Block toAdd = converter.getBlockFromGUIBlock(block);
        blockHandler.addToPA(toAdd);
        resetGameWorld();
    }

    public void deleteFromPA(GUIBlock block) {
        Block toDelete = converter.getBlockFromGUIBlock(block);
        converter.removeBlock(block);
        blockHandler.deleteProgram(toDelete);
        resetGameWorld();
    }

    public GUIBlock getHightlightedBlock() {
        Program program = blockHandler.getPA().getProgram();
        if (program != null) {
            return converter.getGUIBlockFromBlock(program.getCurrentBlock());
        }

        return null;
    }

    public GUIBlock getBlock(String ID, int x, int y) {
        return converter.createNewGUIBlock(ID, x, y);
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
