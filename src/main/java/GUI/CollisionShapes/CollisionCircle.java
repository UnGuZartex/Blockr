package GUI.CollisionShapes;

import Utility.Position;
import java.awt.*;

/**
 * A class for collision circles.
 *
 * @author Alpha-team
 */
public class CollisionCircle extends CollisionShape {

    /**
     * Variable referring to the radius of this circle.
     */
    private int radius;

    /**
     * Initialise a new collision circle with given coordinates, radius and color.
     *
     * @param x The x coordinate for this collision circle.
     * @param y The y coordinate for this collision circle.
     * @param radius The radius for this collision circle.
     * @param color The color for this collision circle.
     *
     * @post The radius of this circle is set to the given radius.
     *
     * @throws IllegalArgumentException
     *         If the given radius is illegal.
     */
    public CollisionCircle(int x, int y, int radius, Color color) {
        super(x, y, color);

        if (radius <= 0) {
            throw new IllegalArgumentException("The given radius is illegal.");
        }

        this.radius = radius;
    }

    /**
     * Get the radius of this collision circle.
     *
     * @return The radius of this collision circle.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Set the radius of this collision circle.
     *
     * @param radius The new radius for this collision circle.
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Check whether this circle intersects with the other circle.
     *
     * @param other The circle to check intersection with.
     *
     * @return True if and only if the distance between the centers of the
     *         two circles are smaller than the sum of their combined radius.
     */
    public boolean intersects(CollisionCircle other) {
        return getPosition().getDistance(other.getPosition()) <= getRadius() + other.getRadius();
    }

    /**
     * Check whether the given coordinates are contained in this circle.
     *
     * @param x The x coordinate of the point to check.
     * @param y The y coordinate of the point to check.
     *
     * @return True if and only if the distance to the circle of the given coordinates
     *         is smaller than the radius of this circle.
     */
    @Override
    public boolean contains(int x, int y) {
        Position checkPos = new Position(x, y);
        return getPosition().getDistance(checkPos) <= radius;
    }

    /**
     * Paint this collision circle.
     *
     * @param g The graphics to paint this shape with.
     *
     * @post This collision shape is painted at its coordinates in its color.
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    /**
     * Paint this collision circle without fill.
     *
     * @param g The graphics to paint this shape with.
     *
     * @post This collision shape is painted on its coordinates.
     */
    @Override
    public void paintNonFill(Graphics g) {
        g.setColor(color);
        g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
    }
}
