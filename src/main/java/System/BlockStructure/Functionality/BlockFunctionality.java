package System.BlockStructure.Functionality;

import System.GameWorld.Level.Level;

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
     * Initialise a new block functionality
     */
    public BlockFunctionality() {}

    /**
     * Get the evaluation of this functionality.
     *
     * @return The evaluation of this functionality.
     */
    public boolean getEvaluation() {
        return evaluation;
    }

    /**
     * Evaluate this functionality with the given level.
     *
     * @param level The level to apply this functionality on.
     */
    public abstract void evaluate(Level level);
}
