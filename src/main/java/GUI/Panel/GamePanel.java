package GUI.Panel;


import GUI.CollisionShapes.CollisionRectangle;
import GUI.Images.ImageLibrary;

import java.awt.*;

public abstract class GamePanel {

    protected static ImageLibrary library;
    protected CollisionRectangle panelRectangle;

    protected GamePanel(int cornerX, int cornerY, int width, int height) {
        panelRectangle = new CollisionRectangle(cornerX, cornerY, width, height, Color.black);
    }

    public CollisionRectangle getPanelRectangle() {
        return panelRectangle;
    }

    public Point getSize() {
        return new Point(panelRectangle.getWidth(), panelRectangle.getHeight());
    }

    public Point getLeftCorner() {
        return new Point(panelRectangle.getX(), panelRectangle.getY());
    }

    public abstract void paint(Graphics g);

    public static void setImageLibrary(ImageLibrary library) {
        GamePanel.library = library;
    }

    protected abstract void drawBackground(Graphics g);
}
