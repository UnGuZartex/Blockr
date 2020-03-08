package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.OperationalBlock;
import System.GameWorld.Level.Level;

public class NotFunctionality extends ConditionalBlockFunctionality<OperationalBlock> {

    @Override
    public void evaluate(Level level) {
        BlockFunctionality functionality = block.getNext().getFunctionality();
        functionality.evaluate(level);
        evaluation = !functionality.getEvaluation();
    }
}
