package BlockStructure.Functionality;

import BlockStructure.Blocks.Block;
import BlockStructure.Blocks.Cavoc;
import BlockStructure.Blocks.ConditionalBlock;
import GameWorld.Level.Level;

public class IfFunctionality extends ConditionalFunctionality {
    private boolean alreadyRan = false;

    @Override
    public void evaluate(Block block, Level level) {
        if (!alreadyRan) {
            Cavoc cavoc = (Cavoc) block;
            ConditionalBlock condition = cavoc.getCondition();
            ConditionalFunctionality functionality = (ConditionalFunctionality) condition.getFunctionality();
            functionality.evaluate(condition, level);
            setEvaluation(functionality.getEvaluation());
            alreadyRan = true;
        }
        else {

            // TODO execute juiste blok hierna naargelang de evaluatie.

            // Reset block
            setEvaluation(false);
            alreadyRan = false;
        }
    }
}
