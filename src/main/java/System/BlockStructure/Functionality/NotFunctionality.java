package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.OperationalBlock;
import System.GameWorld.Level.Level;

/**
 * A class for the not functionality.
 *
 * @author Alpha-team
 */
public class NotFunctionality extends ConditionalBlockFunctionality<OperationalBlock> {

    /**
     * Evaluate this not functionality on the given level and set the evaluation
     * of this functionality to the inverse of the evaluation of the block connected
     * to this functionality.
     *
     * @param level The level to apply this functionality on.
     */
    @Override
    public void evaluate(Level level) {
        BlockFunctionality functionality = block.getNext().getFunctionality();
        functionality.evaluate(level);
        evaluation = !functionality.getEvaluation();
    }
}
