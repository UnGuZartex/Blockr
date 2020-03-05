package BlockStructure.Functionality;

import GameWorld.Level.*;

public class TurnLeftFunctionality implements Functionality {

    @Override
    public void evaluate(Level level) {
        level.getRobot().turnLeft();
    }
}
