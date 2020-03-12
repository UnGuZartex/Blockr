package System.BlockStructure.Functionality;

import System.GameWorld.Level.Level;

/**
 * A class for the functionality which allows a robot to left to the right.
 *
 * @author Alpha-team
 */
public class TurnLeftFunctionality extends BlockFunctionality {

    /**
     * Evaluates this functionality on the given level, thus turning the
     * robot in the given level to the left.
     *
     * @param level The level to apply this functionality on.
     */
    @Override
    public void evaluate(Level level) {
        level.getRobot().turnLeft();
    }
}
