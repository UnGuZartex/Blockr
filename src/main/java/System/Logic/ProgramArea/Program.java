package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.*;
import System.BlockStructure.Connectors.SubConnector;
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
            if (currentBlock != startBlock || !currentBlock.hasAlreadyRan()) {
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
        return startBlock.hasProperConnections();
    }

    public int getSize() {
        return getSizeOfBlock(startBlock);
    }

    private int getSizeOfBlock(Block block) {
        int sizeOfSubConnectList = block.getNbSubConnectors();
        int sum = 1;
        for (int i = 0; i < sizeOfSubConnectList; i++) {
            SubConnector newSubconnector = block.getSubConnectorAt(i);
            if (newSubconnector.isConnected()) {
                sum += getSizeOfBlock(newSubconnector.getConnectedBlock());
            }
        }
        return sum;
    }
}
