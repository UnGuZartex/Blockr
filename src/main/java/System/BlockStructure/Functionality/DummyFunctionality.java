package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;

public class DummyFunctionality extends BlockFunctionality {

    @Override
    public Result evaluate(GameWorld gameWorld) {
        return Result.SUCCESS;
    }
}