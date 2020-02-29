package Main.GUI;

import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;

public class GUIBlock {
    private ArrayList<Rectangle> plugRects = new ArrayList<Rectangle>();
    private ArrayList<Rectangle> socketRects = new ArrayList<Rectangle>();

    public Polygon getPolygon() {
        return polygon;
    }

    private Polygon polygon;
    private Point positionCurrent;
    private Color color;
    private static int connectorWidth = 10, connectorHeight = 10;

    public GUIBlock(int x, int y, int width, int height, Color color) {
        polygon = new Polygon();
        polygon.addPoint(0, 0);

        // Socket (top, facing up)
        polygon.addPoint((width - connectorWidth - 2) / 2, 0);
        polygon.addPoint((width - connectorWidth - 2) / 2, connectorHeight);
        polygon.addPoint((width + connectorWidth + 2) / 2, connectorHeight);
        polygon.addPoint((width + connectorWidth + 2) / 2, 0);

        polygon.addPoint(width, 0);
        polygon.addPoint(width, height);

        // Plug (bottom, facing down)
        polygon.addPoint((width + connectorWidth) / 2, height);
        polygon.addPoint((width + connectorWidth) / 2, height + connectorHeight);
        polygon.addPoint((width - connectorWidth) / 2, height + connectorHeight);
        polygon.addPoint((width - connectorWidth) / 2, height);

        polygon.addPoint(0, height);
        polygon.translate(x, y);

        this.color = color;
        positionCurrent = new Point(x, y);
    }

    public Point getPosition() {
        return positionCurrent;
    }

    public boolean contains(Point p) {
        return polygon.contains(p);
    }

    public boolean collidesWith(Shape other) {
        return polygon.getBounds2D().intersects(other.getBounds2D());
    }

    public void changePosition(int x, int y) {
        polygon.translate(x - positionCurrent.x, y - positionCurrent.y);
        positionCurrent = new Point(x, y);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillPolygon(polygon);
        g.drawPolygon(polygon);
    }
}
