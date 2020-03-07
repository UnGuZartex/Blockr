package System.ProgramArea;

import System.BlockStructure.Blocks.FunctionalBlock;
import System.GameWorld.Level.Level;

import java.util.ArrayList;

public class ProgramArea {
    ArrayList<Program> programs = new ArrayList<>();
    private Level level;
    public ProgramArea(Level level) {
        this.level = level;
    }

    public void addProgram(FunctionalBlock<?> startBlock) {
        programs.add(new Program(startBlock, level));
    }
}
