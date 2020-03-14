package GUI.Blocks;

import GUI.CollisionShapes.CollisionCircle;
import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;
import Utility.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GUIBlock {

    protected int height;
    protected GUIConnector mainConnector;
    protected List<GUIConnector> subConnectors = new ArrayList<>();
    protected List<CollisionRectangle> blockRectangles = new ArrayList<>();
    private String name = "";
    private int x;
    private int y;

    protected GUIBlock(String name, int x, int y) {
        this.name = name;
        setShapes();
        setPosition(x, y);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
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

        g.drawString(name, this.x + 2, this.y + 20);
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

        GUIConnector intersectingConnectorSub, intersectingConnectorMain = null;
        Position staticBlockConnectorPosition = null;
        Position draggedBlockConnector = null;

        if ((intersectingConnectorSub = findCollidingConnector(other.subConnectors, mainConnector)) != null) {
            intersectingConnectorMain = mainConnector;
            draggedBlockConnector = mainConnector.getCollisionCircle().getPosition();
            staticBlockConnectorPosition = intersectingConnectorSub.getCollisionCircle().getPosition();
            setPosition(staticBlockConnectorPosition.getX() + (getX() - draggedBlockConnector.getX()), staticBlockConnectorPosition.getY() + (getY() - draggedBlockConnector.getY()));
            intersectingConnectorMain.connect(intersectingConnectorSub);
            changeHeight(getHeight(), this);

            System.err.println("THIS");
        }
        else if ((intersectingConnectorSub = findCollidingConnector(subConnectors, other.mainConnector)) != null) {
            intersectingConnectorMain = other.mainConnector;
            staticBlockConnectorPosition = other.mainConnector.getCollisionCircle().getPosition();
            draggedBlockConnector = intersectingConnectorSub.getCollisionCircle().getPosition();
            setPosition(staticBlockConnectorPosition.getX() + (getX() - draggedBlockConnector.getX()), staticBlockConnectorPosition.getY() + (getY() - draggedBlockConnector.getY()));
            intersectingConnectorMain.connect(intersectingConnectorSub);
            changeHeight(other.getHeight(), other);

            System.err.println("THIS 2");
        }
    }

    public void disconnect() {
        changeHeight(-getHeight(), this);
        mainConnector.disconnect();
    }

    protected abstract void changeHeight(int heightDelta, GUIBlock previousBlock);

    private GUIConnector findCollidingConnector(List<GUIConnector> subConnectors, GUIConnector mainConnector) {

        for (GUIConnector connector : subConnectors) {
            if (connector.getConnectedConnector() != mainConnector
                    && connector.getCollisionCircle().intersects(mainConnector.getCollisionCircle())) {
                return connector;
            }
        }
        
        return null;
    }

    private void disconnectMainConnector() {
        mainConnector.disconnect();
    }

    protected abstract void setShapes();
}
