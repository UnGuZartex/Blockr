package GUI.Blocks;

import GUI.CollisionShapes.CollisionCircle;
import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GUIBlock {
    private int x;
    private int y;
    protected GUIConnector mainConnector;
    protected List<GUIConnector> subConnectors = new ArrayList<>();
    protected List<CollisionRectangle> blockRectangles = new ArrayList<>();
    private String id;

    protected GUIBlock(String id, int x, int y) {
        this.id = id;
        setShapes();
        setPosition(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void disconnectMainConnector() {
        mainConnector.disconnect();
    }

    public void setPosition(int x, int y) {

        int deltaX = x - this.x;
        int deltaY = y - this.y;

        for (GUIConnector connector: subConnectors) {

            CollisionCircle circle = connector.getCollisionCircle();
            circle.translate(deltaX, deltaY);

            if (connector.isConnected()) {
                connector.getConnectedGUIBlock().translate(deltaX, deltaY);
            }
        }

        CollisionCircle circle = mainConnector.getCollisionCircle();
        circle.translate(deltaX, deltaY);

        for (CollisionRectangle blockRectangle : blockRectangles) {
            blockRectangle.translate(deltaX, deltaY);
        }

        this.x = x;
        this.y = y;
    }

    public void translate(int x, int y) {
        setPosition(this.x + x, this.y + y);
    }

    public void paint(Graphics g) {
        for (GUIConnector connector: subConnectors) {
            connector.getCollisionCircle().paint(g);
        }

        mainConnector.getCollisionCircle().paint(g);

        for (CollisionRectangle blockRectangle : blockRectangles) {
            blockRectangle.paint(g);
        }
    }

    public boolean contains(int x, int y) {
        boolean connectorContains = subConnectors.stream().anyMatch(i -> i.getCollisionCircle().contains(x, y)) || mainConnector.getCollisionCircle().contains(x, y);
        boolean rectangleContains = blockRectangles.stream().anyMatch(i -> i.contains(x, y));

        return connectorContains || rectangleContains;
    }

    public boolean intersectsWithConnector(GUIBlock other) {
        return findCollidingConnector(subConnectors, other.mainConnector) != null || findCollidingConnector(other.subConnectors, mainConnector) != null;
    }

    public void connectWithStaticBlock(GUIBlock other) {

        GUIConnector intersectingConnector;

        if ((intersectingConnector = findCollidingConnector(other.subConnectors, mainConnector)) != null) {
            mainConnector.connect(intersectingConnector);
        }
        else if ((intersectingConnector = findCollidingConnector(subConnectors, other.mainConnector)) != null){
            other.mainConnector.connect(intersectingConnector);
        }
    }

    private GUIConnector findCollidingConnector(List<GUIConnector> subConnectors, GUIConnector mainConnector) {

        for (GUIConnector connector : subConnectors) {
            if (connector.getConnectedConnector() != mainConnector
                    && connector.getCollisionCircle().intersects(mainConnector.getCollisionCircle())) {
                return connector;
            }
        }
        return null;
    }

    protected abstract void setShapes();
}
