package BlockStructure.Functionality;

import GameWorld.Level.*;

public class TurnRightFunctionality implements Functionality {

    @Override
    public void evaluate(Level level) {
        level.getRobot().turnRight();
    }
}
