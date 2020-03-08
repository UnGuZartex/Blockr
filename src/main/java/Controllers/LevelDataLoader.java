package Controllers;

import System.GameState.GameState;
import System.GameWorld.Cell;

import java.awt.*;

public class LevelDataLoader {

    public Point getRobotPosition() {
        return new Point(GameState.currentLevel.getRobot().getX(), GameState.currentLevel.getRobot().getY());
    }

    public Point getGridSize() {
        return new Point(GameState.currentLevel.getGrid().getWidth(), GameState.currentLevel.getGrid().getHeight());
    }

    public Cell[][] getGridCells() {
        return GameState.currentLevel.getGrid().getCells();
    }
}
