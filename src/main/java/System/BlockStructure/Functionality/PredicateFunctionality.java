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

    public PredicateFunctionality(Predicate predicate) {
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
    public Result evaluate(GameWorld gameWorld) {
        evaluation = gameWorld.evaluatePredicate(predicate);
        return Result.SUCCESS;
    }
}
