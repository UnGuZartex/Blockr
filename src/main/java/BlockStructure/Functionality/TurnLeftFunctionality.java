package BlockStructure.Functionality;

import GameWorld.Robot;

public class TurnLeftFunctionality implements Functionality {

    @Override
    public void evaluate(Robot robot) {
        robot.turnLeft();
    }
}
