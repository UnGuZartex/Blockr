package GUI.CollisionShapes;

import java.awt.*;

/**
 * A class for rectangles.
 *
 * @author Alpha-team
 */
public class CollisionRectangle extends CollisionShape {

    /**
     * Variable referring to the width of this collision rectangle.
     */
    private int width;
    /**
     * Variable referring to the height of this collision circle.
     */
    private int height;

    /**
     * Initialise a new collision rectangle with given coordinates, width,
     * height and color.
     *
     * @param x The x coordinate for this collision rectangle.
     * @param y The y coordinate for this collision rectangle.
     * @param width The width for this collision rectangle.
     * @param height The height for this collision rectangle.
     * @param color The color for this collision rectangle.
     *
     * @post The width of this rectangle is set to the given width.
     * @post The height of this rectangle is set to the given height.
     *
     * @throws IllegalArgumentException
     *         If the given width is illegal.
     * @throws IllegalArgumentException
     *         If the given height is illegal.
     */
    public CollisionRectangle(int x, int y, int width, int height, Color color) {
        super(x, y, color);

        if (width <= 0) {
            throw new IllegalArgumentException("The given width is illegal.");
        }

        if (height <= 0) {
            throw new IllegalArgumentException("The given height is illegal.");
        }

        this.width = width;
        this.height = height;
    }

    /**
     * Get the width of this collision rectangle.
     *
     * @return The width of this collision rectangle.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of this collision rectangle.
     *
     * @return The height of this collision rectangle.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the width of this collision rectangle.
     *
     * @param width The new width for this collision rectangle.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Set the height of this collision rectangle.
     *
     * @param height The new height for this collision rectangle.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Checks whether a given rectangle is contained in this
     * rectangle.
     *
     * @param other The rectangle to check.
     *
     * @return True if and only if the given rectangle is contained in this rectangle.
     */
    public boolean contains(CollisionRectangle other) {
        return  x <= other.x && x + width >= other.x + other.width &&
                y <= other.y && y + height >= other.y + other.height;
    }

    /**
     * Check whether the given coordinates are contained into this
     * collision rectangle.
     *
     * @param x The x coordinate of the point to check.
     * @param y The y coordinate of the point to check.
     *
     * @return True if and only if the given x coordinate is between the x
     *         coordinate of this rectangle and the collision with, and the y
     *         coordinate is between the y coordinate of this rectangle and the
     *         height of this rectangle.
     */
    @Override
    public boolean contains(int x, int y) {
        return  x >= this.x && x <= this.x + getCollisionWidth() &&
                y >= this.y && y <= this.y + getCollisionHeight();
    }

    /**
     * Paint this collision rectangle without fill.
     *
     * @param g The graphics to rectangle this shape with.
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
    }

    /**
     * Paint this collision rectangle without fill.
     *
     * @param g The graphics to paint this rectangle with.
     */
    @Override
    public void paintNonFill(Graphics g) {
        g.setColor(color);
        g.drawRect(x, y, width, height);
    }

    /**
     * Get the collision width for this rectangle.
     *
     * @return The width of this rectangle.
     *
     * @post This collision shape is painted at its coordinates in its color.
     */
    private int getCollisionWidth() {
        return width;
    }

    /**
     * Get the collision height of this rectangle.
     *
     * @return The height of this rectangle.
     */
    private int getCollisionHeight() {
        return height;
    }
}
