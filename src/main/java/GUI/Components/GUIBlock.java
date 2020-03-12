package GUI.Components;

import GUI.BlockShape.CollisionCircle;
import GUI.BlockShape.CollisionRectangle;
import Utility.Position;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GUIBlock {
    private int x;
    private int y;
    private List<GUIConnector> connectors = new ArrayList<>();
    private List<CollisionRectangle> blockRectangles = new ArrayList<>();
    private List<GUIBlock> connectedBlocks;

    public GUIBlock(int x, int y) {

        // TODO beter maken
        CollisionRectangle rect = new CollisionRectangle(0, 0, 100, 100, 0, Color.black);
        blockRectangles.add(rect);
        GUIConnector connector = new GUIConnector(this, 0, 0, Color.blue);
        connectors.add(connector);

        connectedBlocks = new ArrayList<>();
        setPosition(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {

        int deltaX = x - this.x;
        int deltaY = y - this.y;

        for (GUIConnector connector: connectors) {

            CollisionCircle circle = connector.getCollisionCircle();
            circle.translate(deltaX, deltaY);
        }

        for (CollisionRectangle blockRectangle : blockRectangles) {
            blockRectangle.translate(deltaX, deltaY);
        }

        for (GUIBlock block : connectedBlocks) {
            block.translate(deltaX, deltaY);
        }

        this.x = x;
        this.y = y;
    }

    public void translate(int x, int y) {
        setPosition(this.x + x, this.y + y);
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

    public boolean intersectsWithConnector(GUIBlock other) {

        for (GUIConnector connector : connectors) {
            if (other.connectors.stream().anyMatch(x -> x.getCollisionCircle().intersects(connector.getCollisionCircle()))) {
                return true;
            }
        }

        return false;
    }

    public void connectWithStaticBlock(GUIBlock other) {

        GUIConnector intersectingConnector;

        for (GUIConnector connector : connectors) {
            intersectingConnector = other.connectors.stream().filter(x -> x.getCollisionCircle().intersects(connector.getCollisionCircle())).findAny().orElse(null);

            if (intersectingConnector != null) {
                connectedBlocks.add(other);
                other.connectedBlocks.add(this);
                setPosition(intersectingConnector.getCollisionCircle().getX(), intersectingConnector.getCollisionCircle().getY());
                break;
            }
        }
    }
}
