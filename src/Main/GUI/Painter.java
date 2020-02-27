package Main.GUI;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Painter {

    protected int cornerX;
    protected int cornerY;
    protected int width;
    protected int height;


    public Painter(int cornerX, int cornerY, int width, int height) {
        this.cornerX = cornerX;
        this.cornerY = cornerY;
        this.width = width;
        this.height = height;
    }

    public abstract void paint(Graphics g);
}
