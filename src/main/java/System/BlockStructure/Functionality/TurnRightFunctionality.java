package System.BlockStructure.Functionality;

import System.GameWorld.Level.Level;

public class TurnRightFunctionality implements IFunctionality {

    @Override
    public void evaluate(Level level) {
        level.getRobot().turnRight();
    }
}
