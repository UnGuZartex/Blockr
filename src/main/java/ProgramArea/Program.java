package ProgramArea;

import BlockStructure.Blocks.*;
import GameWorld.Level.Level;

public class Program {


    private Block currentBlock;
    private Level level;


    public Program(Block start, Level level) {
        currentBlock = start;
        this.level = level;
    }

    public void executeStep() {
        currentBlock.getFunctionality().evaluate(level);
        if (currentBlock.hasNext()){
            currentBlock = currentBlock.getNext();
        } else {
            currentBlock = null;
        }
    }

    public boolean hasWon() {
        return level.hasWon();
    }


}
