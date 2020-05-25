package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;

/**
 * A class for dummy functionalities. These are functionalities which don't do anything.
 *
 * @author Alpha-team
 */
public class DummyFunctionality extends BlockFunctionality {

    /**
     * Evaluate this functionality on the given game world.
     *
     * @param gameWorld The game world to evaluate this functionality on.
     *
     * @post The game world won't change.
     *
     * @return The result SUCCESS.
     */
    @Override
    public Result evaluate(GameWorld gameWorld) {
        return Result.SUCCESS;
    }
}