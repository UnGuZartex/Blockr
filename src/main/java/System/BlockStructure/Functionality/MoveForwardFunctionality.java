package System.BlockStructure.Functionality;

import System.GameWorld.Level.Level;

/**
 * A class for a functionality to move forward.
 *
 * @author Alpha-team
 */
public class MoveForwardFunctionality extends BlockFunctionality {

    /**
     * Evaluate this move forward functionality on the given level.
     *
     * @param level The level to apply this move forward functionality on.
     *
     * @post The robot in the given level has moved one step forward if it was possible.
     */
    @Override
    public void evaluate(Level level) {
        if (level.canMoveForward()) {
            System.out.println("Executed move forward functionality.");
            level.getRobot().moveForward();
        }
    }
}
