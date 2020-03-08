package Controllers;

import System.Logic.ProgramArea.Program;
import System.Logic.ProgramArea.ProgramArea;

public class ProgramController {
    private static final ProgramArea PA = new ProgramArea();

    public void resetProgram() {

    }

    public static void runProgramStep() {
        Program program = PA.getProgram();
    }
}
