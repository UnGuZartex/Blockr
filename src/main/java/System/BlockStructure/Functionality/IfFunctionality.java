package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.Cavoc;
import System.BlockStructure.Blocks.ConditionalBlock;
import System.GameWorld.Level.Level;

public class IfFunctionality extends ConditionalFunctionality {
    private boolean alreadyRan = false;

    @Override
    public void evaluate(Block block, Level level) {
        Cavoc cavoc = (Cavoc) block;

        if (!alreadyRan) {
            ConditionalBlock<ConditionalFunctionality> condition = cavoc.getCondition();
            ConditionalFunctionality functionality = condition.getFunctionality();
            functionality.evaluate(condition, level);
            setEvaluation(functionality.getEvaluation());
            alreadyRan = true;
        }
        else {

            Block nextBlock;

            if (getEvaluation()) {
                nextBlock = cavoc.getCavityPlug().getConnectedConnector().getBlock();
            }
            else {
                nextBlock = cavoc.getBottomPlug().getConnectedConnector().getBlock();
            }

            nextBlock.getFunctionality().evaluate(nextBlock, level);

            // Reset block
            setEvaluation(false);
            alreadyRan = false;
        }
    }
}
