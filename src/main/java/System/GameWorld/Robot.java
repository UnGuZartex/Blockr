package System.GameWorld;

import Controllers.RobotListener;
import Controllers.RobotObserver;
import Utility.Position;

/**
 * A class representing a robot. This has coordinates and a direction.
 *
 * @invar The robot's coordinates must be at all time valid.
 *        | isValidCoordinates(getX(), getY())
 *
 * @author Alpha-team
 */
public class Robot {

    /**
     * Variable referring to the x coordinate of this robot.
     */
    private int x;

    /**
     * Variable referring to the y coordinate of this robot.
     */
    private int y;

    /**
     * Variable referring to the original x coordinate of this robot.
     */
    private int xStart;

    /**
     * Variable referring to the original y coordinate of this robot.
     */
    private int yStart;

    /**
     * Variable referring to the direction of this robot.
     */
    private Direction direction;

    /**
     * Variable referring to the original direction of this robot.
     */
    private Direction directionStart;

    /**
     * Variabele referring to the observer of this class.
     * This observer will notify listeners about the events of the robot.
     */
    private RobotObserver observer;

    /**
     * Initialise a new robot with given x and y coordinates, as
     * well as a direction.
     *
     * @param x The initial x coordinate for this robot.
     * @param y The initial x coordinate for this robot.
     * @param direction The initial direction for this robot.
     *
     * @pre The x and y coordinates must be greater than or
     *      equal to 0.
     *
     * @post The x coordinate of this robot is set to the given x coordinate.
     * @post The y coordinate of this robot is set to the given y coordinate.
     * @post The direction of this robot is set to the given direction.
     * @post The original x coordinate of this robot is set to the given x coordinate.
     * @post The original y coordinate of this robot is set to the given y coordinate.
     * @post The original direction of this robot is set to the given direction.
     * @Post The observer of this robot is created
     */
    public Robot(int x, int y, Direction direction) {

        this.x = x;
        this.y = y;
        this.direction = direction;

        xStart = x;
        yStart = y;
        directionStart = direction;

        observer = new RobotObserver();
    }

    /**
     * Unsubscribe a given robot listener from the robot observer
     *
     * @param listener The given listener
     *
     * @effect the given listener is subscribed to the robot observer
     */
    public void subscribe(RobotListener listener) {
        observer.subscribe(listener);
    }

    /**
     * Subscribe a given robot listener to the robot observer
     *
     * @param listener The given listener
     *
     * @effect the given listener is unsubscribed from the robot observer
     */
    public void unsubscribe(RobotListener listener) {
        observer.unsubscribe(listener);
    }

    /**
     * Checks whether or not the given coordinates are valid for a robot.
     *
     * @param x The x coordinate to check.
     * @param y The Y coordinate to check.
     *
     * @return True if and only if the given coordinates are both greater than 0.
     */
    public static boolean isValidCoordinates(int x, int y) {
        return x >= 0 && y >= 0;
    }

    /**
     * Get the direction of this robot.
     *
     * @return The direction of this robot.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Get the x coordinate of this robot.
     *
     * @return The x coordinate of this robot.
     */
    public int getX() {
        return x;
    }

    /**
     * Get the x coordinate of this robot, one step forward.
     *
     * @return The x coordinate of this robt, when it has stepped
     *         one step in the Direction of this robot.
     */
    public int getXForward() {
        switch (direction) {
            case LEFT:  return x-1;
            case RIGHT: return x+1;
            default:    return getX();
        }
    }

    /**
     * Get the y coordinate of this robot.
     *
     * @return The y coordinate of this robot.
     */
    public int getY() {
        return y;
    }

    /**
     * Get the y coordinate of this robot, one step forward.
     *
     * @return The y coordinate of this robt, when it has stepped
     *         one step in the Direction of this robot.
     */
    public int getYForward() {
        switch (direction) {
            case UP:    return y-1;
            case DOWN:  return y+1;
            default:    return getY();
        }
    }

    /**
     * Moves this robot one step forward in the Direction of
     * this robot.
     *
     * @post The coordinates of this robot are moved forward if this
     *       is still a proper position for this robot.
     *
     * @effect The observer notifies its listeners about the robots new position.
     */
    public void moveForward() {
        int x = getXForward();
        int y = getYForward();
        if (isValidCoordinates(x,y)) {
            this.x = x;
            this.y = y;
        }

        observer.notifyRobotMoved(new Position(x, y));
    }

    /**
     * Turn this robot to the left from its current Direction.
     *
     * @post The direction of this robot is turned to the left.
     *
     * @effect The observer notifies its listeners about the robots new direction
     */
    public void turnLeft() {
        direction = direction.turnLeft();
        observer.notifyRobotChangedDirection(direction);
    }

    /**
     * Turn this robot to the right from its current Direction.
     *
     * @post The direction of this robot is turned to the right.
     *
     * @effect The observer notifies its listeners about the robots new direction.
     */
    public void turnRight() {
        direction = direction.turnRight();
        observer.notifyRobotChangedDirection(direction);
    }

    /**
     * Reset the robot to its original state.
     *
     * @post The direction of this robot is reset to its original value.
     * @post The position of this robot is reset to its original value.
     *
     * @effect The observer notifies its listeners about the robots new position.
     * @effect The observer notifies its listeners about the robots new direction.
     */
    public void reset() {
        x = xStart;
        y = yStart;
        direction = directionStart;

        observer.notifyRobotMoved(new Position(x, y));
        observer.notifyRobotChangedDirection(direction);
    }
}
