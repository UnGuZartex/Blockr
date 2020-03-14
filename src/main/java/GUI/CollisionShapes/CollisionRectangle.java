package GUI.CollisionShapes;

import java.awt.*;

public class CollisionRectangle extends CollisionShape {

    private int width;
    private int height;

    public CollisionRectangle(int x, int y, int width, int height, Color color) {
        super(x, y, color);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean intersects(CollisionRectangle other) {
        return  x < other.x + other.width && x + width > other.x &&
                y < other.y + other.height && y + height > other.y;
    }

    public boolean contains(CollisionRectangle other) {
        return  x < other.x && x + width > other.x + other.width &&
                y < other.y && y + height > other.y + other.height;
    }

    @Override
    public boolean contains(int x, int y) {
        return  x >= this.x && x <= this.x + getCollisionWidth() &&
                y >= this.y && y <= this.y + getCollisionHeight();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
    }

    @Override
    public void paintNonFill(Graphics g) {
        g.setColor(color);
        g.drawRect(x, y, width, height);
    }

    private int getCollisionWidth() {
        return width;
    }

    private int getCollisionHeight() {
        return height;
    }
}
