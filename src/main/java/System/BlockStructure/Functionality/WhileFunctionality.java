package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.WhileBlock;
import System.GameWorld.Level.Level;

public class WhileFunctionality extends ConditionalBlockFunctionality<WhileBlock> {

    @Override
    public void evaluate(Level level) {
        BlockFunctionality functionality = block.getCondition().getFunctionality();
        functionality.evaluate(level);
        evaluation = functionality.getEvaluation();
    }
}
