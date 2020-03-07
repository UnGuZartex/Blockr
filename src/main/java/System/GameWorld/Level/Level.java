package System.GameWorld.Level;

import System.GameWorld.*;
import System.GameWorld.Robot;

import java.awt.*;

public class Level {

    private Grid grid;
    private Robot robot;

    public Level(Point robotPosition, Direction robotDirection, Cell[][] gridCells) {
        this.grid = new Grid(gridCells);
        this.robot = new Robot(robotPosition.x, robotPosition.y, robotDirection);
    }


    public Robot getRobot() {
        return robot;
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean hasWon() {
        return grid.getCellAt(robot.getX(), robot.getY()).getCellType().isWin();
    }

    public boolean canNotWalkHere() {
        return !grid.getCellAt(robot.getX(), robot.getY()).getCellType().canWalkOn();
    }

    public boolean canMoveForward() {
        int x = robot.getXForward();
        int y = robot.getYForward();
        return !grid.getCellAt(x, y).getCellType().canWalkOn() &&
                x > 0 && x < grid.getWidth() &&
                y > 0 && y < grid.getHeight();
    }

    public CellType getTypeForward() {
        return grid.getCellAt(robot.getXForward(), robot.getYForward()).getCellType();
    }
}

