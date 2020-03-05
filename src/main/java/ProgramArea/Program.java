package ProgramArea;

import BlockStructure.Blocks.*;
import GameWorld.Level.Level;

public class Program {

    private FunctionalBlock currentBlock;
    private Level level;


    public Program(FunctionalBlock start, Level level) {
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

    public boolean hasWon() {
        return level.hasWon();
    }
}
