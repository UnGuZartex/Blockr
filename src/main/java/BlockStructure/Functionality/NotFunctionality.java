package BlockStructure.Functionality;

import BlockStructure.Blocks.Block;
import BlockStructure.Blocks.ConditionalBlock;
import BlockStructure.Blocks.OperationalBlock;
import GameWorld.Level.Level;

public class NotFunctionality extends ConditionalFunctionality {

    @Override
    public void evaluate(Block block, Level level) {
        OperationalBlock operation = (OperationalBlock) block;
        ConditionalBlock<ConditionalFunctionality> nextBlock = operation.getNext();
        ConditionalFunctionality functionality = nextBlock.getFunctionality();
        functionality.evaluate(nextBlock, level);
        setEvaluation(!functionality.getEvaluation());
    }

}
