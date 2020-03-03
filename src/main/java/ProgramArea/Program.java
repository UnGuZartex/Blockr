package ProgramArea;

import BlockStructure.*;
import GameWorld.Level.Level;

public class Program {


    private Block currentBlock;
    private Level level;


    public Program(Block start, Level level) {
        currentBlock = start;
        this.level = level;
    }

    public void executeStep() {
        currentBlock.getFunctionality().evaluate(level.getRobot());
        currentBlock = currentBlock.getConnection().getNext();
    }

}
