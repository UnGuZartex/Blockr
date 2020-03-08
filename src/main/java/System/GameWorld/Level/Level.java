package System.GameWorld.Level;

import System.GameWorld.*;
import System.GameWorld.Robot;

import java.awt.*;

public class Level {

    private Grid grid;
    private Robot robot;

    public Level(Point robotPosition, Direction robotDirection, Cell[][] gridCells) {
        // TODO check if robot is in field ? => invariant, must be checked after every robot movement
        this.grid = new Grid(gridCells);
        this.robot = new Robot(robotPosition.x, robotPosition.y, robotDirection);
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
        // TODO return null of throw exception when walk out of bound
        try {
            return grid.getCellAt(robot.getXForward(), robot.getYForward()).getCellType();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}