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
        if (isValidProgram()) {
            if (currentBlock != startBlock || !currentBlock.isAlreadyRan()) {
                System.out.println("Evaluating: " + currentBlock.getFunctionality());
                currentBlock.getFunctionality().evaluate(GameState.currentLevel);
                currentBlock.setAlreadyRan(true);

                Block nextBlock;
                if (!currentBlock.hasNext()) {
                    nextBlock = currentBlock.returnToClosestCavity();
                } else {
                    nextBlock = currentBlock.getNext();
                }
                currentBlock = nextBlock;
            }

        }
    }
    public Block getStartBlock() {
        return startBlock;
    }


    public void resetProgram() {
        currentBlock = startBlock;
        startBlock.reset();

    }

    public boolean hasWon() {
        return GameState.currentLevel.hasWon();
    }

    public boolean isValidProgram() {
        return startBlock.isValid();
    }
}
