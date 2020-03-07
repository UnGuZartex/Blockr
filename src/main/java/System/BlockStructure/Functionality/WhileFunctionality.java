package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.CavityBlock;
import System.BlockStructure.Blocks.ConditionalBlock;
import System.BlockStructure.Blocks.WhileBlock;
import System.GameWorld.Level.Level;

public class WhileFunctionality extends BlockFunctionality<WhileBlock> {

    @Override
    public void evaluate(Level level) {
        ConditionalBlock condition = block.getCondition();
        BlockFunctionality functionality = block.getCondition().getFunctionality();
        functionality.evaluate(level);
        evaluation = functionality.getEvaluation();
    }
}
