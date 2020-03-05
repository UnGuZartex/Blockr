package GameWorld.Level;

import GameWorld.Cell;
import GameWorld.Direction;

import GameWorld.Grid;
import GameWorld.Robot;

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

    public boolean hasWon() {
        return grid.getCellAt(robot.getX(), robot.getY()).getCellType().isWin();
    }

    public boolean canNotWalkHere() {
        return !grid.getCellAt(robot.getX(), robot.getY()).getCellType().canWalkOn();
    }
}
