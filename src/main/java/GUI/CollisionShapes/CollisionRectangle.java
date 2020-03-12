package GUI.CollisionShapes;

import java.awt.*;

public class CollisionRectangle extends CollisionShape {

    private int width;
    private int height;

    public CollisionRectangle(int x, int y, int width, int height, int collisionDelta, Color color) {
        super(x, y, collisionDelta, color);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean contains(int x, int y) {
        return  x >= this.x && x <= this.x + getCollisionWidth() &&
                y >= this.y && y <= this.y + getCollisionHeight();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(x, y, width, height);
    }

    private int getCollisionWidth() {
        return width + collisionDelta;
    }

    private int getCollisionHeight() {
        return height + collisionDelta;
    }
}
