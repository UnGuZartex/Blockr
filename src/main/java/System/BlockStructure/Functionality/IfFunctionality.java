package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.IfBlock;
import System.GameWorld.Level.Level;

public class IfFunctionality extends ConditionalBlockFunctionality<IfBlock> {

    private boolean alreadyRan = false;

    @Override
    public void evaluate(Level level) {
        if (!alreadyRan) {
            BlockFunctionality functionality = block.getCondition().getFunctionality();
            functionality.evaluate(level);
            evaluation = functionality.getEvaluation();
            alreadyRan = true;
        }
        else {
            block.getNext().getFunctionality().evaluate(level);

            // Reset block
            evaluation = false;
            alreadyRan = false;
        }
    }
}
