package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.WallInFrontBlock;
import System.GameWorld.CellType;
import System.GameWorld.Level.Level;

/**
 * A class for the conditional functionality to check if there is
 * a wall in front of the robot.
 *
 * @author Alpha-team
 */
public class WallInFrontFunctionality extends ConditionalBlockFunctionality<WallInFrontBlock> {

    /**
     * Evaluates this wall in front functionality and set the evaluation
     * of this functionality to the correct value, based on whether or not
     * a wall is in front of the robot in the given level.
     *
     * @param level The level to apply this functionality on.
     */
    @Override
    public void evaluate(Level level) {
        evaluation = (level.getTypeForward() == CellType.WALL);
    }
}
