package Main.GameWorld.Level;

import Main.GameWorld.Cell;
import Main.GameWorld.Direction;
import Main.GameWorld.Grid;
import Main.GameWorld.Robot;

import java.awt.*;

public class Level {

    private Grid grid;
    private Robot robot;

    public Level(Point robotPosition, Direction robotDirection, Cell[][] gridCells) {
        this.grid = new Grid(gridCells);
        this.robot = new Robot(robotPosition.x, robotPosition.y, robotDirection);
    }

    public boolean hasWon() {
        return grid.getCellAt(robot.getX(), robot.getY()).getCellType().isWin();
    }

    public boolean canNotWalkHere() {
        return !grid.getCellAt(robot.getX(), robot.getY()).getCellType().canWalkOn();
    }
}
