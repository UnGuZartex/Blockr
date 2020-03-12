package GUI.Components;

import GUI.BlockShape.CollisionCircle;
import GUI.BlockShape.CollisionRectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUIBlock {
    private int x;
    private int y;
    private GUIConnector mainConnector;
    private List<GUIConnector> subConnectors = new ArrayList<>();
    private List<CollisionRectangle> blockRectangles = new ArrayList<>();

    public GUIBlock(int x, int y) {

        // TODO beter maken
        CollisionRectangle rect = new CollisionRectangle(0, 0, 150, 80, 0, Color.black);
        blockRectangles.add(rect);

        mainConnector = new GUIConnector(this, 75, 0, Color.blue);
        subConnectors.add(new GUIConnector(this, 75, 80, Color.red));
        setPosition(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void disconnectMain() {
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
            }
        }

        if (intersectingConnector == null) {

            for (GUIConnector connector : subConnectors) {
                if (connector.getConnectedConnector() != other.mainConnector
                        && connector.getCollisionCircle().intersects(other.mainConnector.getCollisionCircle())) {
                    intersectingConnector = connector;
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
}
