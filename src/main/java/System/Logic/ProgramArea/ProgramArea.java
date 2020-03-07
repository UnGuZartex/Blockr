package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;

import java.util.ArrayList;

public class ProgramArea {
    ArrayList<Program> programs = new ArrayList<>();
    public ProgramArea() {
    }

    public void addProgram(Block<?> startBlock) {
        programs.add(new Program(startBlock));
    }
}
