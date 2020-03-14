package GUI.CollisionShapes;

import Utility.Position;

import java.awt.*;

public class CollisionCircle extends CollisionShape {

    private int radius;

    public CollisionCircle(int x, int y, int radius, Color color) {
        super(x, y, color);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean intersects(CollisionCircle other) {
        return getPosition().getDistance(other.getPosition()) < getRadius() + other.getRadius();
    }

    @Override
    public Position getPosition() {
        return new Position(x, y);
    }

    @Override
    public boolean contains(int x, int y) {
        Position checkPos = new Position(x, y);
        return getPosition().getDistance(checkPos) < radius;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Override
    public void paintNonFill(Graphics g) {
        g.setColor(color);
        g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
    }
}
