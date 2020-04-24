package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import System.BlockStructure.Blocks.CavityBlock;

/**
 * A class for cavity functionalities.
 *
 * @author Alpha-team
 */
public class CavityFunctionality extends ConditionalBlockFunctionality<CavityBlock> {

    /**
     * Evaluate this functionality on the given gameWorld.
     *
     * @post The evaluation is set to the evaluation of the condition
     *       of the block of this functionality.
     *
     * @return A cavity functionality will always be executable and result in SUCCESS.
     */
    @Override
    public Result evaluate(GameWorld gameWorld) {
        try {
            BlockFunctionality functionality = block.getCondition().getFunctionality();
            functionality.evaluate(gameWorld);
            evaluation = functionality.getEvaluation();
        } catch (NullPointerException ignore) {
            evaluation = false;
        }
        return Result.SUCCESS;
    }
}