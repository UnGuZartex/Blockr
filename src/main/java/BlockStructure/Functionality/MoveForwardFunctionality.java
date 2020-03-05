package BlockStructure.Functionality;

import GameWorld.Level.Level;

public class MoveForwardFunctionality implements Functionality {

    @Override
    public void evaluate(Level level) {
        if (level.canMoveForward()) {
            level.getRobot().moveForward();
        }
    }
}
