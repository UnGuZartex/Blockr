package Controllers;

import System.GameWorld.Direction;
import Utility.Position;

/**
 * A class used as a listener for receiving events about the robot state.
 *
 * @author Alpha-team
 */
public interface RobotListener {

    /**
     * This event is called when the robot has moved.
     *
     * @param newPosition the new position of the robot.
     */
    void onRobotMoved(Position newPosition);

    /**
     * This event is called when the robot changed direction.
     *
     * @param newDirection the new direction of the robot.
     */
    void onRobotChangedDirection(Direction newDirection);
}
