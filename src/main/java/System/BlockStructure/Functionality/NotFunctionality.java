package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import System.BlockStructure.Blocks.OperationalBlock;
import System.GameWorld.Level.Level;

/**
 * A class for the not functionality.
 *
 * @author Alpha-team
 */
public class NotFunctionality extends ConditionalBlockFunctionality<OperationalBlock> {

    /**
     * Evaluate this not functionality on the given gameWorld and set the evaluation
     * of this functionality to the inverse of the evaluation of the block connected
     * to this functionality.
     *
     * @param gameWorld The gameWorld to apply this functionality on.
     *
     * @post Set the evaluation of this functionality to the inverse of the evaluation
     *       of the functionality of the block of this functionality.
     *
     * @return SUCCESS because a evaluation of boolean is always successful
     */
    @Override
    public Result evaluate(GameWorld gameWorld) {
        BlockFunctionality functionality = block.getNext().getFunctionality();
        functionality.evaluate(gameWorld);
        evaluation = !functionality.getEvaluation();
        return Result.SUCCES;
    }
}
