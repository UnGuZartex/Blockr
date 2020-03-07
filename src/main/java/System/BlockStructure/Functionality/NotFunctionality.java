package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.ConditionalBlock;
import System.BlockStructure.Blocks.OperationalBlock;
import System.GameWorld.Level.Level;

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
