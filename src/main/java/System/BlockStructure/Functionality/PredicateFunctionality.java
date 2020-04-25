package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Predicate;
import System.BlockStructure.Blocks.PredicateBlock;

/**
 * A class for predicate functionalities, which are conditional functionalities.
 *
 * @invar A predicate functionality must have a valid predicate.
 *        | isValidPredicate(predicate)
 *
 * @author Alpha-team
 */
public class PredicateFunctionality extends ConditionalBlockFunctionality<PredicateBlock> {

    /**
     * Variable referring to the predicate of this functionality.
     */
    private final Predicate predicate;

    /**
     * Initialise a new predicate functionality with given predicate.
     *
     * @param predicate The predicate for this functionality.
     *
     * @post The predicate of this functionality is set to the given predicate.
     *
     * @throws IllegalArgumentException
     *         When the given predicate is not valid.
     */
    public PredicateFunctionality(Predicate predicate) throws IllegalArgumentException {
        if (!isValidPredicate(predicate)) {
            throw new IllegalArgumentException("The given predicate is not valid!");
        }
        this.predicate = predicate;
    }

    /**
     * Check whether or not the given predicate is valid.
     *
     * @param predicate The predicate to check.
     *
     * @return True if and only if the given predicate is not null.
     */
    public static boolean isValidPredicate(Predicate predicate) {
        return predicate != null;
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
    public Result evaluate(GameWorld gameWorld) {
        evaluation = gameWorld.evaluatePredicate(predicate);
        return Result.SUCCESS;
    }
}