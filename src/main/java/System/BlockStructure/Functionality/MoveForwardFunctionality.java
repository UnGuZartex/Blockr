package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.Block;
import System.GameWorld.Level.Level;

public class MoveForwardFunctionality implements Functionality {

    @Override
    public void evaluate(Block block, Level level) {
        if (level.canMoveForward()) {
            level.getRobot().moveForward();
        }
    }
}
