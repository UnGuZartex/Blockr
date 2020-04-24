package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Action;

/**
 * A class for action functionalities.
 *
 * @author Alpha-team
 */
public class ActionFunctionality extends BlockFunctionality {

    /**
     * Variable referring to the action of this action functionality.
     */
    private final Action action;

    /**
     * Initialise a new action functionality with given game world and action.
     *
     * @param action The action for this functionality.
     * @param gameWorld The game world for this functionality.
     *
     * @effect The super constructor is called with given game world.
     *
     * @post The action of this functionality is set to the given action.
     */
    public ActionFunctionality(Action action, GameWorld gameWorld) {
        super(gameWorld);
        this.action = action;
    }

    /**
     * Evaluate this functionality.
     *
     * @return The result of the action execution of the action of this
     *         functionality on the game world of this functionality.
     */
    @Override
    public Result evaluate() {
        return gameWorld.executeAction(action);
    }

    /**
     * Copy this functionality
     *
     * @return A new action functionality with the same action and game world
     *         as this functionality.
     */
    @Override
    public BlockFunctionality copy() {
        return new ActionFunctionality(action, gameWorld);
    }
}
