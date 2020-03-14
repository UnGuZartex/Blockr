package Utility;

/**
 * A class for positions with x,y coordinates
 *
 * @author Alpha-team
 */
public class Position {

    /**
     * Variable referring to the x-coordinate of this position.
     */
    private final int x;
    /**
     * Variable referring to the y-coordinate of this position.
     */
    private final int y;

    /**
     * Initialise a new position with given x and y coordinates.
     *
     * @param x The x-coordinate for this position.
     * @param y The y-coordinate for this position.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x-coordinate of this position.
     *
     * @return The x-coordinate of this position.
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y-coordinate of this position.
     *
     * @return The y-coordinate of this position.
     */
    public int getY() {
        return  y;
    }

    /**
     * Get the distance between this position and the given position.
     *
     * @param other The position to compute the distance to.
     *
     * @return The euclidean distance between this position and the given position
     *         using the Pythagorean theorem.
     */
    public double getDistance(Position other) {
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
    }
}
