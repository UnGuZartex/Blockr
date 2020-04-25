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
     * @return SUCCESS, the cavity has no effect on the game world and will always be a success.
     * @post The evaluation is set to the evaluation of the condition
     * of the block of this functionality.
     */
    @Override
    public Result evaluate(GameWorld gameWorld) {
        try {
            BlockFunctionality functionality = block.getCondition().getFunctionality();
            functionality.evaluate(gameWorld);
            evaluation = functionality.getEvaluation();
        } catch (NullPointerException e) {
            evaluation = false;
        }

        return Result.SUCCESS;
    }
}

