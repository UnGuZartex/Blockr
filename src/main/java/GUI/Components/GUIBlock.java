package GUI.Components;

import GUI.BlockShape.CollisionCircle;
import GUI.BlockShape.CollisionRectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUIBlock {
    private int x;
    private int y;
    List<GUIConnector> connectors = new ArrayList<>();
    List<CollisionRectangle> blockRectangles = new ArrayList<>();

    public GUIBlock(int x, int y) {

        // TODO beter maken
        CollisionRectangle rect = new CollisionRectangle(0, 0, 100, 100, 0, Color.black);
        blockRectangles.add(rect);
        GUIConnector connector = new GUIConnector(this, 20, 0, Color.blue);
        connectors.add(connector);

        setPosition(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {

        for (GUIConnector connector: connectors) {

            CollisionCircle circle = connector.getCollisionCircle();
            int deltaX = circle.getX() - this.x;
            int deltaY = circle.getY() - this.y;

            circle.setX(x + deltaX);
            circle.setY(y + deltaY);
        }

        for (CollisionRectangle blockRectangle : blockRectangles) {
            int deltaX =  blockRectangle.getX() - this.x;
            int deltaY =  blockRectangle.getY() - this.y;

            blockRectangle.setX(x + deltaX);
            blockRectangle.setY(y + deltaY);
        }

        this.x = x;
        this.y = y;
    }

    public void paint(Graphics g) {
        for (GUIConnector connector: connectors) {
            connector.getCollisionCircle().paint(g);
        }

        for (CollisionRectangle blockRectangle : blockRectangles) {
            blockRectangle.paint(g);
        }
    }

    public boolean contains(int x, int y) {
        boolean connectorContains = connectors.stream().anyMatch(i -> i.getCollisionCircle().contains(x, y));
        boolean rectangleContains = blockRectangles.stream().anyMatch(i -> i.contains(x, y));

        return connectorContains || rectangleContains;
    }

    public boolean intersects(GUIBlock other) {
        return false;
    }
}
