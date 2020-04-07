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
     * Initialise a new block functionality
     *
     * @param gameWorld the gameworld this functionality is linked to
     */
    public CavityFunctionality(GameWorld gameWorld) {
        super(gameWorld);
    }

    /**
     * Evaluate this functionality on the given gameWorld.
     *
     * @post The evaluation is set to the evaluation of the condition
     *       of the block of this functionality.
     *
     * @return SUCCESS, the cavity has no effect on the gameworld and will always be a success.
     */
    @Override
    public Result evaluate() {
        try {
            BlockFunctionality functionality = block.getCondition().getFunctionality();
            functionality.evaluate();
            evaluation = functionality.getEvaluation();
        } catch (NullPointerException e) {
            evaluation = false;
        }

        return Result.SUCCES;
    }

}
