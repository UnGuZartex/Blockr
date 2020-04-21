package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.History.Snapshot;

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
     * Variable referring to the game world this functionality acts upon.
     */
    protected final GameWorld gameWorld;
    /**
     * Variable referring to the latest snapshot of this functionality.
     */
    protected Snapshot snapshot;

    /**
     * Initialize a new block functionality.
     *
     * @param gameWorld the game world this functionality is linked to
     */
    public BlockFunctionality(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    /**
     * Get the evaluation of this functionality.
     *
     * @return the evaluation of this functionality.
     */
    public boolean getEvaluation() {
        return evaluation;
    }

    /**
     * Return the game world linked to this functionality.
     *
     * @return the game world linked to this functionality.
     */
    public GameWorld getGameWorld() {
        return gameWorld;
    }

    /**
     * Evaluate this functionality with the game world of this functionality.
     *
     * @effect Evaluates this functionality on the game world of this functionality.
     */
    public abstract Result evaluate();

    /**
     * Copy this functionality
     *
     * @return A new functionality.
     */
    public abstract BlockFunctionality copy();

    /**
     * Execute this functionality.
     *
     * @effect A back up has been taken.
     *
     * @return The result this functionality evaluates to.
     */
    public Result execute() {
        this.snapshot = gameWorld.createSnapshot();
        return evaluate();
    }

    /**
     * Load the current snapshot
     *
     * @post The snapshot of this functionality is set to the state of the game world
     *       before the method call.
     *
     * @effect The current snapshot is loaded into the game world of this functionality.
     */
    public void loadCurrentSnapshot() {
        Snapshot newSnapshot = gameWorld.createSnapshot();
        gameWorld.loadSnapshot(snapshot);
        snapshot = newSnapshot;
    }
}
