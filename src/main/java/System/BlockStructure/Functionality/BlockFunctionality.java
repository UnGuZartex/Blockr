package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;

/**
 * An abstract class for functionality's which can be attached to a block.
 *
 * @author Alpha-team
 */
public abstract class BlockFunctionality {

    /**
     * Variable referring to evaluation of this functionality.
     */
    protected boolean evaluation;

    protected final GameWorld gameWorld;

    /**
     * Initialise a new block functionality
     * @param gameWorld the gameworld this functionality is linked to
     */
    public BlockFunctionality(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    /**
     * Get the evaluation of this functionality.
     *
     * @return The evaluation of this functionality.
     */
    public boolean getEvaluation() {
        return evaluation;
    }

    /**
     * Evaluate this functionality with the given gameWorld.
     *
     * @effect Evaluates this functionality on the given level.
     */
    public abstract Result evaluate();

    public GameWorld getGameWorld() {
        return gameWorld;
    }
}
