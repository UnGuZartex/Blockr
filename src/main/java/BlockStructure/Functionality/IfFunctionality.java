package BlockStructure.Functionality;

import BlockStructure.Blocks.Block;
import BlockStructure.Blocks.Cavoc;
import BlockStructure.Blocks.ConditionalBlock;
import GameWorld.Level.Level;

public class IfFunctionality extends ConditionalFunctionality {
    private boolean alreadyRan = false;

    @Override
    public void evaluate(Block<?> block, Level level) {
        Cavoc cavoc = (Cavoc) block;

        if (!alreadyRan) {
            ConditionalBlock<ConditionalFunctionality> condition = cavoc.getCondition();
            ConditionalFunctionality functionality = condition.getFunctionality();
            functionality.evaluate(condition, level);
            setEvaluation(functionality.getEvaluation());
            alreadyRan = true;
        }
        else {

            Block<?> nextBlock;

            if (getEvaluation()) {
                nextBlock = cavoc.getCavityPlug().getSocket().getBlock();
            }
            else {
                nextBlock = cavoc.getBottomPlug().getSocket().getBlock();
            }

            nextBlock.getFunctionality().evaluate(nextBlock, level);

            // Reset block
            setEvaluation(false);
            alreadyRan = false;
        }
    }
}
