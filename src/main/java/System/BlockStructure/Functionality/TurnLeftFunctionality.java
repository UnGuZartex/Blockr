package System.BlockStructure.Functionality;

import System.GameWorld.Level.Level;

public class TurnLeftFunctionality extends BlockFunctionality {

    @Override
    public void evaluate(Level level) {
        level.getRobot().turnLeft();
    }
}
