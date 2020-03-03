package Main.BlockStructure.Functionality;

import Main.GameWorld.Robot;

public class TurnLeftFunctionality implements Functionality {

    @Override
    public void evaluate(Robot robot) {
        robot.turnLeft();
    }
}
