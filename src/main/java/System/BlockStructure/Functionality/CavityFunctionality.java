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
     * @param gameWorld the game world this functionality is linked to
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
     * @return SUCCESS, the cavity has no effect on the game world and will always be a success.
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

        return Result.SUCCESS;
    }

    /**
     * Get a copy of this functionality.
     *
     * @return A new cavity functionality which has the same block and game world as this functionality.
     */
    @Override
    public BlockFunctionality copy() {
        CavityFunctionality func = new CavityFunctionality(gameWorld);
        func.setBlock(this.block);
        return func;
    }
}