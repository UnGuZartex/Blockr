package BlockStructure.Functionality;

import BlockStructure.Blocks.Block;
import GameWorld.Level.Level;

public class NotFunctionality extends ConditionalFunctionality {

    @Override
    public void evaluate(Block block, Level level) {
        Block nextBlock = block.getNext();
        ConditionalFunctionality functionality = (ConditionalFunctionality)nextBlock.getFunctionality();
        functionality.evaluate(nextBlock, level);
        setEvaluation(!functionality.getEvaluation());
    }

}
