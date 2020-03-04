package ProgramArea;

import BlockStructure.Blocks.*;
import GameWorld.Level.Level;

public class Program {


    private Block currentBlock;
    private Level level;


    public Program(FunctionalBlock start, Level level) {
        currentBlock = start;
        this.level = level;
    }

    public void executeStep() {
        //currentBlock.getFunctionality().evaluate(level.getRobot());
        if (currentBlock.hasNext()){
            currentBlock = currentBlock.getNext();
        } else {
            currentBlock = null;
        }
    }


}
