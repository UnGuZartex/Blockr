package Controllers;

import System.BlockStructure.Blocks.IfBlock;
import System.Logic.ProgramArea.Program;
import System.Logic.ProgramArea.ProgramArea;

public class ProgramController {
    private static final ProgramArea PA = new ProgramArea();

    public static void addBlock(IfBlock ifBlock) {
        PA.addProgram(ifBlock);
    }

    public void resetProgram() {

    }

    public static void runProgramStep() {
        Program program = PA.getProgram();
        program.executeStep();
    }
}
