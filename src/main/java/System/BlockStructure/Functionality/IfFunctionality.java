package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.IfBlock;
import System.GameWorld.Level.Level;

public class IfFunctionality extends ConditionalBlockFunctionality<IfBlock> {


    @Override
    public void evaluate(Level level) {
        BlockFunctionality functionality = block.getCondition().getFunctionality();
        functionality.evaluate(level);
        evaluation = functionality.getEvaluation();
    }

}
