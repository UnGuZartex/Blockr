package System.GameWorld;

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
     * Variable referring to the direction of this robot.
     */
    private Direction direction;

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
     */
    public Robot(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
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
     */
    public void moveForward() {
        int x = getXForward();
        int y = getYForward();
        if (isValidCoordinates(x,y)) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Turn this robot to the left from its current Direction.
     *
     * @post The direction of this robot is turned to the left.
     */
    public void turnLeft() {
        direction = direction.turnLeft();
    }

    /**
     * Turn this robot to the right from its current Direction.
     *
     * @post The direction of this robot is turned to the rigth.
     */
    public void turnRight() {
        direction = direction.turnRight();
    }
}
