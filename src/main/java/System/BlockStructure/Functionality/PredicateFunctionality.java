package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Predicate;
import System.BlockStructure.Blocks.StatementBlock;

/**
 * A class for predicate functionalities, which are conditional functionalities.
 *
 * @author Alpha-team
 */
public class PredicateFunctionality extends ConditionalBlockFunctionality<StatementBlock> {

    /**
     * Variable referring to the predicate of this functionality.
     */
    private final Predicate predicate;

    /**
     * Initialise a new predicate functionality with given game world and action.
     *
     * @param predicate The predicate for this functionality.
     * @param gameWorld The game world for this functionality.
     *
     * @effect The super constructor is called with given game world.
     *
     * @post The predicate of this functionality is set to the given predicate.
     */
    public PredicateFunctionality(Predicate predicate, GameWorld gameWorld) {
        super(gameWorld);
        this.predicate = predicate;
    }

    /**
     * Evaluate this functionality.
     *
     * @effect The evaluation is set to the evaluation of the predicate of this
     *         functionality in the game world of this functionality.
     *
     * @return The success result.
     */
    @Override
    public Result evaluate() {
        evaluation = gameWorld.evaluatePredicate(predicate);
        return Result.SUCCESS;
    }

    /**
     * Copy this functionality
     *
     * @return A new predicate functionality with the same predicate and game world
     *         as this functionality.
     */
    @Override
    public BlockFunctionality copy() {
        PredicateFunctionality func = new PredicateFunctionality(predicate, gameWorld);
        func.setBlock(this.block);
        return func;
    }
}
