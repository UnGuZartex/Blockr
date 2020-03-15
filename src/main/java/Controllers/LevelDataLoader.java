package Controllers;

import System.GameState.GameState;
import System.GameWorld.Cell;
import System.GameWorld.Direction;
import Utility.Position;

import java.awt.*;

public class LevelDataLoader {

    public Position getRobotPosition() {
        return new Position(GameState.getCurrentLevel().getRobot().getX(), GameState.getCurrentLevel().getRobot().getY());
    }

    public Direction getRobotDirection() {
        System.err.println("ROBOT POINTER: " + GameState.getCurrentLevel().getRobot());
        return GameState.getCurrentLevel().getRobot().getDirection();
    }

    public Position getGridSize() {
        return new Position(GameState.getCurrentLevel().getGrid().getWidth(), GameState.getCurrentLevel().getGrid().getHeight());
    }

    public Cell[][] getGridCells() {
        return GameState.getCurrentLevel().getGrid().getCells();
    }

    public void subscribe(RobotListener listener) {
        GameState.getCurrentLevel().getRobot().subscribe(listener);
    }
}
