package GUI.CollisionShapes;

import Utility.Position;

import java.awt.*;

public abstract class CollisionShape {

    protected int x, y, collisionDelta;
    private Color color;

    protected CollisionShape(int x, int y, int collisionDelta, Color color) {
        this.x = x;
        this.y = y;
        this.collisionDelta = collisionDelta;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void translate(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public Position getPosition() {
        return new Position(x, y);
    }

    public void paint(Graphics g) {
        g.setColor(color);
    }

    public abstract boolean contains(int x, int y);
}
