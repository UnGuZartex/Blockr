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

    protected GUIBlock(int x, int y) {
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
                System.err.println("CONNECTED");
            }
            else {
                System.err.println("NOT CONNECTED");
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
        boolean connectorContains = subConnectors.stream().anyMatch(i -> i.getCollisionCircle().contains(x, y));
        boolean rectangleContains = blockRectangles.stream().anyMatch(i -> i.contains(x, y));

        return connectorContains || rectangleContains;
    }

    public boolean intersectsWithConnector(GUIBlock other) {

        for (GUIConnector connector : subConnectors) {
            if (other.mainConnector.getCollisionCircle().intersects(connector.getCollisionCircle())
            && connector.getConnectedConnector() != other.mainConnector) {
                return true;
            }
        }

        for (GUIConnector connector : other.subConnectors) {
            if (mainConnector.getCollisionCircle().intersects(connector.getCollisionCircle())
            && connector.getConnectedConnector() != mainConnector) {
                return true;
            }
        }

        return false;
    }

    public void connectWithStaticBlock(GUIBlock other) {

        GUIConnector intersectingConnector = null;

        for (GUIConnector connector : other.subConnectors) {
            if (connector.getConnectedConnector() != mainConnector
                    && connector.getCollisionCircle().intersects(mainConnector.getCollisionCircle())) {
                intersectingConnector = connector;
                break;
            }
        }

        if (intersectingConnector == null) {

            for (GUIConnector connector : subConnectors) {
                System.err.println("BOOLEAN " + (connector.getConnectedConnector() != other.mainConnector) + " "  + connector.getCollisionCircle().intersects(other.mainConnector.getCollisionCircle()));
                if (connector.getConnectedConnector() != other.mainConnector
                        && connector.getCollisionCircle().intersects(other.mainConnector.getCollisionCircle())) {
                    intersectingConnector = connector;
                    break;
                }
            }

            if (intersectingConnector == null) {
                return;
            }

            other.mainConnector.connect(intersectingConnector);


            System.err.println("SUB " + intersectingConnector.isConnected());
        }
        else {
            mainConnector.connect(intersectingConnector);
            System.err.println("MAIN: other: " + intersectingConnector.getCollisionCircle().getPosition() + " " + mainConnector.getCollisionCircle().getPosition());
        }

        //setPosition(0,0);
    }

    protected abstract void setShapes();
}
