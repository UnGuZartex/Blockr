package GUI;

import GUI.Images.ImageLibrary;

import java.awt.*;

public abstract class Painter {

    protected static ImageLibrary library;
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

    protected abstract void drawBackground(Graphics g);

    public static void setImageLibrary(ImageLibrary library) {
        Painter.library = library;
    }

    public abstract void paint(Graphics g);
}
