package System.BlockStructure.Functionality;

import System.GameWorld.Level.Level;

public class MoveForwardFunctionality extends BlockFunctionality {

    @Override
    public void evaluate(Level level) {
        if (level.canMoveForward()) {
            level.getRobot().moveForward();
        }
    }
}
