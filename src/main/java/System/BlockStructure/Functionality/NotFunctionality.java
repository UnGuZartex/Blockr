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
     * Initialise a new block functionality
     *
     * @param gameWorld the game world this functionality is linked to
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
     * @return SUCCESS because an evaluation of a boolean is always successful.
     */
    @Override
    public Result evaluate() {
        BlockFunctionality functionality = block.getNext().getFunctionality();
        functionality.evaluate();
        evaluation = !functionality.getEvaluation();
        return Result.SUCCESS;
    }

    /**
     * Get a copy of this functionality.
     *
     * @return A new not functionality which has the same block and game world as this functionality.
     */
    @Override
    public BlockFunctionality copy() {
        NotFunctionality func = new NotFunctionality(gameWorld);
        func.setBlock(this.block);
        return func;
    }
}
