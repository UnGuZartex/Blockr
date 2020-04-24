package GUI.Panel;

import GUI.CollisionShapes.CollisionRectangle;
import Images.ImageLibrary;

import java.awt.*;

/**
 * An abstract class for gaming panels.
 *
 * @author Alpha-team
 */
public abstract class GamePanel {

    /**
     * Variable referring to the area of the panel.
     */
    protected CollisionRectangle panelRectangle;

    /**
     * Initialise a new game panel with given corner coordinates and dimension.
     *
     * @param cornerX The x coordinate of the corner of the area.
     * @param cornerY The y coordinate of the corner of the area.
     * @param width The width of the area.
     * @param height The height of the area.
     *
     * @effect The panel rectangle is set to a new rectangle with given parameters
     *         the color black.
     */
    protected GamePanel(int cornerX, int cornerY, int width, int height) {
        panelRectangle = new CollisionRectangle(cornerX, cornerY, width, height, Color.lightGray);
    }

    /**
     * Get the area of this game panel.
     *
     * @return The collision rectangle representing this game panel.
     */
    public CollisionRectangle getPanelRectangle() {
        return panelRectangle;
    }

    /**
     * Get the size of this panel.
     *
     * @return A point with the width and height.
     */
    public Point getSize() {
        return new Point(panelRectangle.getWidth(), panelRectangle.getHeight());
    }

    /**
     * Get the left corner of this panel.
     *
     * @return The coordinate of the left upper corner of this panel.
     */
    public Point getLeftCorner() {
        return new Point(panelRectangle.getX(), panelRectangle.getY());
    }

    /**
     * Paint this panel.
     *
     * @param g The graphics to paint this panel with.
     */
    public abstract void paint(Graphics g, ImageLibrary images);

    /**
     * Draw the background of this panel.
     *
     * @param g The graphics to draw the background with.
     *
     * @effect The panel rectangle is drawn
     */
    protected void drawBackground(Graphics g) {
        panelRectangle.paint(g);
    }
}
