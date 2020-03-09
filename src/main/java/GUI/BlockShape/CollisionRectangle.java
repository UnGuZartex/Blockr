package GUI.BlockShape;

import java.awt.*;

public class CollisionRectangle extends CollisionShape {

    public int width;
    public int height;

    public CollisionRectangle(int x, int y, int width, int height, int collisionDelta) {
        super(x, y, collisionDelta);
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean contains(int x, int y) {
        return  x >= this.x && x <= this.x + width + collisionDelta &&
                y >= this.y && y <= this.y + height + collisionDelta;
    }

    @Override
    public void paint(Graphics g) {
        g.fillRect(x, y, width, height);
    }
}
