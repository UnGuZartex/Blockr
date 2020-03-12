package Controllers;

import GUI.GUIBlock;
import GUI.GUISocket;
import System.BlockStructure.Blocks.Block;
import System.Logic.Converter;
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.Program;
import System.Logic.ProgramArea.ProgramArea;

import java.util.List;

public class ProgramController {
    private final PABlockHandler blockHandler = new PABlockHandler();
    private final Converter converter = new Converter();

    public void addBlockToPA(GUIBlock block) {
        Block toAdd = converter.convert(block.getID());
        blockHandler.addToPA(toAdd);
    }

    public void connectToSubConnector(GUIBlock block, GUISocket socket) {
        Block toAdd = converter.convert(block.getID());
        blockHandler.addToPA(toAdd);
    }

    //TODO REMOVE V
    public void addBlock(Block block){
        blockHandler.addToPA(block);
    }

    public boolean reachedMaxBlocks() {
        return blockHandler.getMaxReached();
    }


    public void resetProgram() {
        Program program = blockHandler.getPA().getProgram();
        program.resetProgram();

    }

    public void runProgramStep() {
        Program program = blockHandler.getPA().getProgram();
        program.executeStep();
    }




}
