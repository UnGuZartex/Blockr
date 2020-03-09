package System.GameWorld;

/**
 * A class representing a robot. This has coordinates and a direction.
 *
 * TODO invariant isValidCoordinates() and apply on functions
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
     *       equal to 0.
     */
    public Robot(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

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
     */
    public void moveForward() {
        x = getXForward();
        y = getYForward();
    }

    /**
     * Turn this robot to the left from its current Direction.
     */
    public void turnLeft() {
        direction = direction.turnLeft();
    }

    /**
     * Turn this robot to the right from its current Direction.
     */
    public void turnRight() {
        direction = direction.turnRight();
    }
}
