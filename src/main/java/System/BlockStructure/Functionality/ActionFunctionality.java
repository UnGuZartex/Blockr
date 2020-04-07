package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Action;

public class ActionFunctionality extends BlockFunctionality {

    private final Action action;

    public ActionFunctionality(Action action, GameWorld gameWorld) {
        super(gameWorld);
        this.action = action;
    }

    @Override
    public Result evaluate() {
        return gameWorld.executeAction(action);
    }
}
