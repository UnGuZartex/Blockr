package Controllers;

import System.GameState.GameState;
import System.GameWorld.Cell;
import System.GameWorld.Direction;
import Utility.Position;

import java.awt.*;

/**
 * A class used as a static level data loader.
 * Is only used as a first loader to load the level data the first time.
 *
 * @author Alpha-team
 */
public class LevelDataLoader {

    /**
     * Return the current robot position
     * @return the current robot position
     */
    public Position getRobotPosition() {
        return new Position(GameState.getCurrentLevel().getRobot().getX(), GameState.getCurrentLevel().getRobot().getY());
    }

    /**
     * Return the current robot direction
     * @return the current robot direction
     */
    public Direction getRobotDirection() {
        return GameState.getCurrentLevel().getRobot().getDirection();
    }

    /**
     * Return the current grid size
     * @return the current grid size
     */
    public Position getGridSize() {
        return new Position(GameState.getCurrentLevel().getGrid().getWidth(), GameState.getCurrentLevel().getGrid().getHeight());
    }

    /**
     * Return the current array of grid cells
     * @return the current array of grid cells
     */
    public Cell[][] getGridCells() {
        return GameState.getCurrentLevel().getGrid().getCells();
    }

    /**
     * Subscribe the given robot listener to the current robot observer.
     *
     * @param listener the given robot listener.
     */
    public void subscribe(RobotListener listener) {
        GameState.getCurrentLevel().getRobot().subscribe(listener);
    }
}
