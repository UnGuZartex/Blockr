package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import System.BlockStructure.Blocks.OperationalBlock;

/**
 * A class for the not functionality.
 *
 * @author Alpha-team
 */
public class NotFunctionality extends ConditionalBlockFunctionality<OperationalBlock> {

    /**
     * Evaluate this not functionality on the given gameWorld.
     *
     * @post Set the evaluation of this functionality to the inverse of the evaluation
     *       of the functionality of the block of this functionality.
     *
     * @return SUCCESS because an evaluation of a boolean is always successful.
     */
    @Override
    public Result evaluate(GameWorld gameWorld) {
        BlockFunctionality functionality = block.getNext().getFunctionality();
        functionality.evaluate(gameWorld);
        evaluation = !functionality.getEvaluation();
        return Result.SUCCESS;
    }
}