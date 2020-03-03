package BlockStructure.Functionality;

import GameWorld.Robot;

public class MoveForwardFunctionality implements Functionality {

    @Override
    public void evaluate(Robot robot) {
        robot.moveForward();
    }
}
