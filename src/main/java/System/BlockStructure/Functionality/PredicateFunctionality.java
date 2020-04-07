package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Predicate;
import System.BlockStructure.Blocks.StatementBlock;

public class PredicateFunctionality extends ConditionalBlockFunctionality<StatementBlock> {
    private final Predicate predicate;

    public PredicateFunctionality(Predicate predicate, GameWorld gameWorld) {
        super(gameWorld);
        this.predicate = predicate;
    }

    @Override
    public Result evaluate() {
        evaluation = gameWorld.evaluatePredicate(predicate);
        return Result.SUCCES;
    }
}
