package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.*;
import System.GameState.GameState;

public class Program {

    private Block<?> startBlock;
    private Block<?> currentBlock;

    public Program(Block<?> start) {
        startBlock = start;
        currentBlock = start;
    }

    public void executeStep() {
        if (currentBlock != null && currentBlock.hasNext()) {
            currentBlock.getFunctionality().evaluate(GameState.currentLevel);
            currentBlock = currentBlock.getNext();
        } else {
            currentBlock = null;
        }
    }

    public void resetProgram() {
        currentBlock = startBlock;
    }

    public boolean hasWon() {
        return GameState.currentLevel.hasWon();
    }
}
