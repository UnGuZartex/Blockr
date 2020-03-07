package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.CavityBlock;
import System.BlockStructure.Blocks.ConditionalBlock;
import System.GameWorld.Level.Level;

public class IfFunctionality extends ConditionalFunctionality {
    private boolean alreadyRan = false;

    @Override
    public void evaluate(Level level) {
        CavityBlock cavoc = (CavityBlock) block;

        if (!alreadyRan) {
            ConditionalBlock condition = cavoc.getCondition();
            ConditionalFunctionality functionality = condition.getFunctionality();
            functionality.evaluate(level);
            setEvaluation(functionality.getEvaluation());
            alreadyRan = true;
        }
        else {
            cavoc.getNext().getFunctionality().evaluate(level);

            // Reset block
            setEvaluation(false);
            alreadyRan = false;
        }
    }
}
