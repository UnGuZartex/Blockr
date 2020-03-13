package Controllers;

import System.GameState.GameState;
import System.GameWorld.Cell;
import System.GameWorld.Direction;

import java.awt.*;

public class LevelDataLoader {

    public Point getRobotPosition() {
        return new Point(GameState.getCurrentLevel().getRobot().getX(), GameState.getCurrentLevel().getRobot().getY());
    }

    public Direction getRobotDirection() {
        return GameState.getCurrentLevel().getRobot().getDirection();
    }

    public Point getGridSize() {
        return new Point(GameState.getCurrentLevel().getGrid().getWidth(), GameState.getCurrentLevel().getGrid().getHeight());
    }

    public Cell[][] getGridCells() {
        return GameState.getCurrentLevel().getGrid().getCells();
    }
}
