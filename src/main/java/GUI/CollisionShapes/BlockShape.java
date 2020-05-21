package GUI.CollisionShapes;

import java.awt.*;
import java.util.List;

// TODO RECTANGLES LIJST EN CONNECTOR LIJST MOETEN BEIDE SIZE > 1 hebben!!!!!!! (invar)
public class BlockShape {
    private Color color;
    private List<CollisionRectangle> rectangles;
    private List<CollisionCircle> circles;

    public BlockShape (List<CollisionRectangle> rectangles, List<CollisionCircle> circles) {
        this.rectangles = rectangles;
        this.circles = circles;
    }

    public void translate(int x, int y) {
        rectangles.forEach(s -> s.translate(x, y));
        circles.forEach(s -> s.translate(x, y));
    }

    public void setColor(Color color) {
        rectangles.forEach(s -> s.setColor(color));
    }

    public Color getColor() {
        return rectangles.get(0).getColor();
    }

    public void paint(Graphics g) {
        rectangles.forEach(s -> s.paint(g));
        circles.forEach(s -> s.paint(g));
    }
}
