package GUI;

import java.awt.*;
import java.util.ArrayList;

public class GUIBlock {
    private ArrayList<Rectangle> plugRects = new ArrayList<Rectangle>();
    private ArrayList<Rectangle> socketRects = new ArrayList<Rectangle>();
    private Polygon polygon;
    private Point positionCurrent;
    private Color color;
    private static final int CONNECTOR_WIDTH = 15, CONNECTOR_WIDTH_DELTA = 4, CONNECTOR_HEIGHT = 10;

    public GUIBlock(int x, int y, int width, int height, Color color) {
        polygon = initPolygonNormalBlock(width, height);
        polygon.translate(x, y);
        this.color = color;
        positionCurrent = new Point(x, y);
    }

    public Polygon getPolygon() {
        return polygon;
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
        g.setColor(Color.black);
        g.drawPolygon(polygon);
    }

    private Polygon initPolygonNormalBlock(int width, int height) {

        polygon = new Polygon();
        polygon.addPoint(0, 0);

        // Socket (top, facing up)
        polygon.addPoint((width - CONNECTOR_WIDTH - 2) / 2, 0);
        polygon.addPoint((width - CONNECTOR_WIDTH - 2 + CONNECTOR_WIDTH_DELTA) / 2, CONNECTOR_HEIGHT);
        polygon.addPoint((width + CONNECTOR_WIDTH + 2 - CONNECTOR_WIDTH_DELTA) / 2, CONNECTOR_HEIGHT);
        polygon.addPoint((width + CONNECTOR_WIDTH + 2) / 2, 0);

        polygon.addPoint(width, 0);
        polygon.addPoint(width, height);

        // Plug (bottom, facing down)
        polygon.addPoint((width + CONNECTOR_WIDTH) / 2, height);
        polygon.addPoint((width + CONNECTOR_WIDTH - CONNECTOR_WIDTH_DELTA) / 2, height + CONNECTOR_HEIGHT);
        polygon.addPoint((width - CONNECTOR_WIDTH + CONNECTOR_WIDTH_DELTA) / 2, height + CONNECTOR_HEIGHT);
        polygon.addPoint((width - CONNECTOR_WIDTH) / 2, height);

        polygon.addPoint(0, height);
        return polygon;
    }

    private Polygon initPolygonNormalConditionalBlock(int width, int height) {

        polygon = new Polygon();
        polygon.addPoint(0, 0);

        // Socket (top, facing up)
        polygon.addPoint((width - CONNECTOR_WIDTH - 2) / 2, 0);
        polygon.addPoint((width - CONNECTOR_WIDTH - 2) / 2, CONNECTOR_HEIGHT);
        polygon.addPoint((width + CONNECTOR_WIDTH + 2) / 2, CONNECTOR_HEIGHT);
        polygon.addPoint((width + CONNECTOR_WIDTH + 2) / 2, 0);

        polygon.addPoint(width, 0);
        polygon.addPoint(width, height);

        // Plug (bottom, facing down)
        polygon.addPoint((width + CONNECTOR_WIDTH) / 2, height);
        polygon.addPoint((width + CONNECTOR_WIDTH) / 2, height + CONNECTOR_HEIGHT);
        polygon.addPoint((width - CONNECTOR_WIDTH) / 2, height + CONNECTOR_HEIGHT);
        polygon.addPoint((width - CONNECTOR_WIDTH) / 2, height);

        polygon.addPoint(0, height);
        return polygon;
    }
}
