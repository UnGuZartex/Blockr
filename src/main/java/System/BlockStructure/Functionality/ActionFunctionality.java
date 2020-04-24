package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Action;

/**
 * A class for action functionalities.
 *
 * @invar An action functionality must have a valid action.
 *        | isValidAction(action);
 *
 * @author Alpha-team
 */
public class ActionFunctionality extends BlockFunctionality {

    /**
     * Variable referring to the action of this action functionality.
     */
    private final Action action;

    /**
     * Initialise a new action functionality with given action.
     *
     * @param action The action for this functionality.
     *
     * @post The action of this functionality is set to the given action.
     *
     * @throws IllegalArgumentException
     *         When the given action is not valid.
     */
    public ActionFunctionality(Action action) throws IllegalArgumentException {
        if (!isValidAction(action)) {
            throw new IllegalArgumentException("The given action is not valid!");
        }
        this.action = action;
    }

    /**
     * Check whether or not the given action is valid.
     *
     * @param action The action to check.
     *
     * @return True if and only if the given action is effective.
     */
    public static boolean isValidAction(Action action) {
        return action != null;
    }

    /**
     * Evaluate this functionality.
     *
     * @return The result when the action of this functionality is executed
     *         on the given game world.
     */
    @Override
    public Result evaluate(GameWorld gameWorld) {
        return gameWorld.executeAction(action);
    }
}