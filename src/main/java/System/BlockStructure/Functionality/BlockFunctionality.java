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
     * Evaluate this functionality with the given game world.
     *
     * @effect Evaluates this functionality on the given game world.
     */
    public abstract Result evaluate();

    /**
     * Return the game world linked to this functionality.
     *
     * @return the game world linked to this functionality.
     */
    public GameWorld getGameWorld() {
        return gameWorld;
    }


    public Result execute() {
        this.snapshot = gameWorld.createSnapshot();
        return evaluate();
    }

    public void undo() {
        Snapshot redosnapshot = gameWorld.createSnapshot();
        gameWorld.loadSnapshot(snapshot);
        snapshot = redosnapshot;
    }

    public void redo() {
        Snapshot undosnapshot = gameWorld.createSnapshot();
        gameWorld.loadSnapshot(snapshot);
        snapshot = undosnapshot;
    }

    public abstract BlockFunctionality copy();
}
