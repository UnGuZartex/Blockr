package Controllers;

import System.BlockStructure.Blocks.Block;
import System.Logic.ProgramArea.Program;
import System.Logic.ProgramArea.ProgramArea;

public class ProgramController {
    private static final ProgramArea PA = new ProgramArea();

    public static void addBlock(Block block) {
        PA.addProgram(block);
    }

    public static void resetProgram() {
        Program program = PA.getProgram();
        program.resetProgram();

    }

    public static void runProgramStep() {
        Program program = PA.getProgram();
        program.executeStep();
    }
}
