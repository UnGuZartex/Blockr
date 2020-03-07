package System.ProgramArea;

import System.BlockStructure.Blocks.FunctionalBlock;

import java.util.ArrayList;

public class ProgramArea {
    ArrayList<Program> programs = new ArrayList<>();
    public ProgramArea() {
    }

    public void addProgram(FunctionalBlock<?> startBlock) {
        programs.add(new Program(startBlock));
    }
}
