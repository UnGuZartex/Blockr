package GUI.BlockShape;

import Utility.Position;

import java.awt.*;

public class CollisionCircle extends CollisionShape {

    private int radius;

    public CollisionCircle(int x, int y, int radius, int collisionDelta, Color color) {
        super(x, y, collisionDelta, color);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public boolean intersects(CollisionCircle other) {
        return getPosition().getDistance(other.getPosition()) >= getCollisionRadius() + other.getCollisionRadius();
    }

    @Override
    public Position getPosition() {
        return new Position(x, y);
    }

    @Override
    public boolean contains(int x, int y) {
        Position checkPos = new Position(x, y);
        return getPosition().getDistance(checkPos) <= radius + collisionDelta;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    private int getCollisionRadius() {
        return radius + collisionDelta;
    }
}
