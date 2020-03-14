package GUI.CollisionShapes;

import Utility.Position;

import java.awt.*;

public abstract class CollisionShape {

    protected int x, y;
    protected Color color;

    protected CollisionShape(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void translate(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public Position getPosition() {
        return new Position(x, y);
    }

    public abstract void paint(Graphics g);

    public abstract void paintNonFill(Graphics g);

    public abstract boolean contains(int x, int y);
}
