package System.BlockStructure.Functionality;

import System.GameWorld.Level.Level;

public class MoveForwardFunctionality implements IFunctionality {

    @Override
    public void evaluate(Level level) {
        if (level.canMoveForward()) {
            level.getRobot().moveForward();
        }
    }
}
