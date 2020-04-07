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
     * Initialise a new block functionality
     *
     * @param gameWorld the gameworld this functionality is linked to
     */
    public NotFunctionality(GameWorld gameWorld) {
        super(gameWorld);
    }

    /**
     * Evaluate this not functionality on the given gameWorld and set the evaluation
     * of this functionality to the inverse of the evaluation of the block connected
     * to this functionality.
     *
     * @post Set the evaluation of this functionality to the inverse of the evaluation
     *       of the functionality of the block of this functionality.
     *
     * @return SUCCESS because a evaluation of boolean is always successful
     */
    @Override
    public Result evaluate() {
        BlockFunctionality functionality = block.getNext().getFunctionality();
        functionality.evaluate();
        evaluation = !functionality.getEvaluation();
        return Result.SUCCES;
    }
}
