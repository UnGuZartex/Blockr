package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.CavityBlock;
import System.BlockStructure.Blocks.IfBlock;
import System.GameWorld.Level.Level;

/**
 * A class for cavity functionalities.
 *
 * @author Alpha-team
 */
public class CavityFunctionality extends ConditionalBlockFunctionality<CavityBlock> {

    /**
     * Evaluate this functionality on the given level.
     *
     * @param level The level to apply this functionality on.
     *
     * @post The evaluation is set to the evaluation of the condition
     *       of the block of this functionality.
     */
    @Override
    public void evaluate(Level level) {
        try {
            BlockFunctionality functionality = block.getCondition().getFunctionality();
            functionality.evaluate(level);
            evaluation = functionality.getEvaluation();
        } catch (NullPointerException e) {
            evaluation = false;
        }
    }

}
