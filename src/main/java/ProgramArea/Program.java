package ProgramArea;

import BlockStructure.Blocks.*;
import GameWorld.Level.Level;

public class Program {

    private FunctionalBlock startBlock;
    private FunctionalBlock currentBlock;
    private Level level;

    public Program(FunctionalBlock start, Level level) {
        startBlock = start;
        currentBlock = start;
        this.level = level;
    }

    public void executeStep() {
        if (currentBlock != null && currentBlock.hasNext()) {
            currentBlock.getFunctionality().evaluate(currentBlock, level);
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
