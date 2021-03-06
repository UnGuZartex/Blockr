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

    /**
     * Get the evaluation of this functionality.
     *
     * @return the evaluation of this functionality.
     */
    public boolean getEvaluation() {
        return evaluation;
    }

    /**
     * Evaluate this functionality with the given game world.
     *
     * @effect Evaluates this functionality on the given game world.
     */
    public abstract Result evaluate(GameWorld gameWorld);
}
