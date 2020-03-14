package System.GameWorld.Level;

import System.GameWorld.*;
import System.GameWorld.Robot;
import Utility.Position;


/**
 * A class for the level, containing a Robot and a grid.
 *
 * @invar The coordinates for the robot must be a valid position in the grid.
 *        | isValidRobotPositionInCells(getRobot().getX(), getRobot().getY(), getGrid().getCells())
 *
 * @author Alpha-team
 */
public class Level {

    /**
     * Variable referring to the robot in this level.
     */
    private final Robot robot;
    /**
     * Variable referring to the grid in this level.
     */
    private final Grid grid;

    /**
     * Initialise a new level with given robot positiona and direction,
     * as well as the cells for the grid of the level.
     *
     * @param robotPosition The position for the robot of this level.
     * @param robotDirection The direction for the robot of this level.
     * @param gridCells The cells for the grid of this level.
     *
     * @throws IllegalArgumentException
     *         When the given robot position is an invalid position in the cells.
     */
    public Level(Position robotPosition, Direction robotDirection, Cell[][] gridCells) throws IllegalArgumentException {
        if (!isValidRobotPositionInCells(robotPosition.getX(), robotPosition.getY(), gridCells)) {
            throw new IllegalArgumentException("The given robot position is invalid in the cells");
        }
        this.robot = new Robot(robotPosition.getX(), robotPosition.getY(), robotDirection);
        this.grid = new Grid(gridCells);
    }

    /**
     * Check whether or not given robot coordinates and grid are valid.
     *
     * @param x The x coordinate for the robot to check.
     * @param y The y coordinate for the robot to check.
     * @param cells The cells to check.
     *
     * @return True if and only if the given coordinates are smaller than
     *         the dimensions of the cells and greater than 0 and the given
     *         coordinates correspond to a cell in the given cells which has
     *         a walkable CellType.
     */
    public static boolean isValidRobotPositionInCells(int x, int y, Cell[][] cells) {
        return x < cells.length && x >= 0 &&
               y < cells[0].length && y >= 0 &&
               cells[x][y].getCellType().canWalkOn();
   }

    /**
     * Get the robot of this Level.
     *
     * @return the robot of this Level.
     */
    public Robot getRobot() {
        return robot;
    }

    /**
     * Get the grid of this Level.
     *
     * @return The grid of this Level.
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Checks whether or not the level has been won.
     *
     * @return True if and only if the robot's position corresponds
     *         to a cell which with CellType which is win.
     */
    public boolean hasWon() {
        return grid.getCellAt(robot.getX(), robot.getY()).getCellType().isWin();
    }

    /**
     * Checks whether or not the robot can not walk on its current position.
     *
     * @return True if and only if the position of the robot corresponds
     *         with a Cell which has not a walkable CellType.
     */
    public boolean canNotWalkHere() {
        return !grid.getCellAt(robot.getX(), robot.getY()).getCellType().canWalkOn();
    }

    /**
     * Checks whether or not the robot can move forward.
     *
     * @return True if and only if the position of the robot when
     *         it moves forward is still in the grid (x between 0
     *         and width, y between 0 and height) and if the cell
     *         in front of the robot is walkable.
     */
    public boolean canMoveForward() {
        int x = robot.getXForward();
        int y = robot.getYForward();
        return x >= 0 && x < grid.getWidth() &&
               y >= 0 && y < grid.getHeight() &&
               getTypeForward().canWalkOn();
    }

    /**
     * Returns the CellType the robot is standing on if it would
     * walk forward.
     *
     * @return The CellType of the cell corresponding to the cell the
     *         robot would stand on if it moved one step forward, even
     *         though if the cell is not walkable. If the robot would
     *         walk out of the grid, null is returned.
     */
    public CellType getTypeForward() {
        try {
            return grid.getCellAt(robot.getXForward(), robot.getYForward()).getCellType();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}