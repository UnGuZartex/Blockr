package Main.BlockStructure.Functionality;

import Main.GameWorld.Robot;

public class TurnRightFunctionality implements Functionality {

    @Override
    public void evaluate(Robot robot) {
        robot.turnRight();
    }
}
