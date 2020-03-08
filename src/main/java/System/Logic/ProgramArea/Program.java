package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.*;
import System.GameState.GameState;

public class Program {

    private Block startBlock;
    private Block currentBlock;

    public Program(Block start) {
        startBlock = start;
        currentBlock = start;
    }

    public void executeStep() {
        if (currentBlock != null) {
            System.out.println("Evaluating: " + currentBlock.getFunctionality());
            currentBlock.getFunctionality().evaluate(GameState.currentLevel);
            Block nextBlock = null;
            if (currentBlock.hasNext()) {
                nextBlock = currentBlock.getNext();
                while (nextBlock.getSkip() && currentBlock.hasNext()) {
                    nextBlock = nextBlock.getNext();
                }
            }
            currentBlock.getFunctionality().reset();
            currentBlock = nextBlock;
        } else {
            if (hasWon()){
                System.out.println("YOU WIN!");
            }
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
