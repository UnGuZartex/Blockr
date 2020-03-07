package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.CavityBlock;
import System.BlockStructure.Blocks.ConditionalBlock;
import System.GameWorld.Level.Level;

public class WhileFunctionality extends CavityFunctionality {

    public WhileFunctionality(int blockId) {
        super(blockId);
    }

    @Override
    public void evaluate(Level level) {
        CavityBlock cavoc = block;

        ConditionalBlock condition = cavoc.getCondition();
        ConditionalFunctionality functionality = condition.getFunctionality();
        functionality.evaluate(level);
        setEvaluation(functionality.getEvaluation());

    }
}
