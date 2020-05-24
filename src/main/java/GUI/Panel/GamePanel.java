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
    protected final CollisionRectangle panelRectangle;

    /**
     * Initialise a new game panel with given corner coordinates and dimension.
     *
     * @param cornerX The x coordinate of the corner of the area.
     * @param cornerY The y coordinate of the corner of the area.
     * @param width The width of the area.
     * @param height The height of the area.
     *
     * @effect The panel rectangle is set to a new rectangle with given parameters and the colour light gray.
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
     * Paint this panel.
     *
     * @param g The given graphics.
     * @param library The image library.
     */
    abstract void paint(Graphics g, ImageLibrary library);

    /**
     * Paint the panel background.
     *
     * @param g The given graphics.
     * @param library The image library.
     * @param imageName The name of the image to draw.
     *
     * @effect The panel boundaries are drawn.
     * @effect The panel background is drawn if an image name is given.
     */
    protected void drawBackGround(Graphics g, ImageLibrary library, String imageName) {

        if (imageName != null) {
            g.drawImage(library.getImage(imageName), panelRectangle.getX(),
                    panelRectangle.getY(), panelRectangle.getWidth(), panelRectangle.getHeight(), null);
        }

        g.drawRect(panelRectangle.getX(), panelRectangle.getY(), panelRectangle.getWidth(), panelRectangle.getHeight());
    }
}
