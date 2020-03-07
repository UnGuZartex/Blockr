package System.ProgramArea;

import System.BlockStructure.Blocks.*;
import System.GameWorld.Level.Level;

public class Program {

    private BasicBlock startBlock;
    private BasicBlock currentBlock;
    private Level level;

    public Program(BasicBlock start, Level level) {
        startBlock = start;
        currentBlock = start;
        this.level = level;
    }

    public void executeStep() {
        if (currentBlock != null && currentBlock.hasNext()) {
            currentBlock.getFunctionality().evaluate(level);
            currentBlock = currentBlock.getNext();
        } else {
            currentBlock = null;
        }
    }

    public void resetProgram() {
        currentBlock = startBlock;
    }

    public boolean hasWon() {
        return level.hasWon();
    }
}
