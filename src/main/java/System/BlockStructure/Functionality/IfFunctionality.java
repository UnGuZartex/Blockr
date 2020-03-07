package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.CavityBlock;
import System.BlockStructure.Blocks.ConditionalBlock;
import System.GameWorld.Level.Level;

public class IfFunctionality extends CavityFunctionality {
    private boolean alreadyRan = false;

    public IfFunctionality(int blockId) {
        super(blockId);
    }

    @Override
    public void evaluate(Level level) {
        if (!alreadyRan) {
            ConditionalBlock condition = block.getCondition();
            ConditionalFunctionality functionality = condition.getFunctionality();
            functionality.evaluate(level);
            setEvaluation(functionality.getEvaluation());
            alreadyRan = true;
        }        else {
            block.getNext().getFunctionality().evaluate(level);

            // Reset block
            setEvaluation(false);
            alreadyRan = false;
        }
    }
}
