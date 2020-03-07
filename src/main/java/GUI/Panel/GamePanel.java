package GUI.Panel;

import GUI.Painters.Painter;

import java.awt.*;

public abstract class GamePanel {

    private int cornerX;
    private int cornerY;
    private int width;
    private int height;

    protected GamePanel(int cornerX, int cornerY, int width, int height) {
        this.cornerX = cornerX;
        this.cornerY = cornerY;
        this.width = width;
        this.height = height;
    }

    public Point getSize() {
        return new Point(width, height);
    }

    public Point getLeftCorner() {
        return new Point(cornerX, cornerY);
    }

    public abstract void paint(Graphics g);
}
