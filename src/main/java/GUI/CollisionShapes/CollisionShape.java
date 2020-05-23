package GUI.CollisionShapes;

import Utility.Position;

import java.awt.*;

/**
 * An abstract class for shapes which can collide with other shapes.
 *
 * @invar A collision shape must have a valid color at all time.
 *        | isValidColor(color)
 *
 * @author Alpha-team
 */
public abstract class CollisionShape {

    /**
     * Variables referring to the x,y coordinates of this collision shape.
     */
    protected int x, y;
    /**
     * Variable referring to the color of this collision shape.
     */
    protected Color color;

    /**
     * Initialise a new collision shape with given x,y coordinates and color.
     *
     * @param x The x coordinate for this collision shape.
     * @param y The y coordinate for this collision shape.
     * @param color The color for this collision shape.
     *
     * @post The x-coordinate of this collision shape is set to the given x-coordinate.
     * @post The y-coordinate of this collision shape is set to the given y-coordinate.
     * @post The color of this collision shape is set to the given color.
     *
     * @throws IllegalArgumentException
     *         If the given color is not valid.
     */
    protected CollisionShape(int x, int y, Color color) throws IllegalArgumentException {
        if (!isValidColor(color)) {
            throw new IllegalArgumentException("Given color is not valid!");
        }
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * Check whether or not the given color is valid or not.
     *
     * @param color The color to check.
     *
     * @return True if and only if the given color is effective.
     */
    public static boolean isValidColor(Color color) {
        return color != null;
    }

    /**
     * Get the x coordinate of this collision shape.
     *
     * @return The x coordinate of this collision shape.
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y coordinate of this collision shape.
     *
     * @return The y coordinate of this collision shape.
     */
    public int getY() {
        return y;
    }

    /**
     * Set the x coordinate for this collision shape.
     *
     * @param x The new x coordinate for this collision shape.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the y coordinate for this collision shape.
     *
     * @param y The new y coordinate for this collision shape.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Set the coordinates of this collision shape.
     *
     * @param x The new x coordinate for this collision shape.
     * @param y The new y coordinate for this collision shape.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the color of this collision shape.
     *
     * @return The color of this collision shape.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Set the color of this collision shape.
     *
     * @param color The new color for this collision shape.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Translate this collision shape with the given amount in the
     * x and y direction.
     *
     * @param x The amount to translate in the x direction.
     * @param y The amount to translate in the y direction.
     *
     * @post The coordinates of this shape are increased with the given amounts.
     */
    public void translate(int x, int y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Get the position of this collision shape.
     *
     * @return The position of this collision shape.
     */
    public Position getPosition() {
        return new Position(x, y);
    }

    /**
     * Paint this collision shape.
     *
     * @param g The graphics to paint this shape with.
     *
     * @post This collision shape is painted at its coordinates in its color.
     */
    public abstract void paint(Graphics g);

    /**
     * Paint this collision shape without fill.
     *
     * @param g The graphics to paint this shape with.
     *
     * @post This collision shape is painted on its coordinates.
     */
    public abstract void paintNonFill(Graphics g);

    /**
     * Checks whether the given coordinates are contained in
     * this collision shape.
     *
     * @param x The x coordinate of the point to check.
     * @param y The y coordinate of the point to check.
     *
     * @return True if and only if the given coordinates represent a
     *         coordinate in this collision shape.
     */
    public abstract boolean contains(int x, int y);
}
