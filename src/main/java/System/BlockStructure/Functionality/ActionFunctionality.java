package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Action;

public class ActionFunctionality extends BlockFunctionality {

    private final Action action;

    public ActionFunctionality(Action action) {
        this.action = action;
    }

    @Override
    public Result evaluate(GameWorld gameWorld) {
        return gameWorld.executeAction(action);
    }
}
