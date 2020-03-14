package GUI.Blocks;

import Controllers.ConnectionController;
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
    private String name;
    private String id;
    private int x;
    private int y;

    protected GUIBlock(String name, String id, int x, int y) {
        this.name = name;
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

    public String getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public void setColor(Color color) {
        for (CollisionRectangle rectangle : blockRectangles) {
            rectangle.setColor(color);
        }
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
        return blockRectangles.stream().anyMatch(i -> i.contains(x, y));
    }

    public boolean isInside(CollisionRectangle area) {
        return blockRectangles.stream().allMatch(area::contains);
    }

    public boolean intersectsWithConnector(GUIBlock other) {
        return findCollidingConnector(subConnectors, other.mainConnector) != null || findCollidingConnector(other.subConnectors, mainConnector) != null;
    }

    public void connectWithStaticBlock(GUIBlock other, ConnectionController controller) {

        GUIConnector intersectingConnectorSub;
        Position staticBlockConnectorPosition, draggedBlockConnectorPosition;
        GUIBlock main, sub;

        if ((intersectingConnectorSub = findCollidingConnector(other.subConnectors, mainConnector)) != null) {
            main = this;
            sub = other;
            draggedBlockConnectorPosition = mainConnector.getCollisionCircle().getPosition();
            staticBlockConnectorPosition = intersectingConnectorSub.getCollisionCircle().getPosition();
        }
        else if ((intersectingConnectorSub = findCollidingConnector(subConnectors, other.mainConnector)) != null) {
            sub = this;
            main = other;
            staticBlockConnectorPosition = other.mainConnector.getCollisionCircle().getPosition();
            draggedBlockConnectorPosition = intersectingConnectorSub.getCollisionCircle().getPosition();
        }
        else {
            throw new IllegalArgumentException("Given block does not have a colliding connector!");
        }

        if (controller.isValidConnection(main, sub, intersectingConnectorSub.getId())) {

            if (!mainConnector.isConnected()) {
                setPosition(staticBlockConnectorPosition.getX() + (getX() - draggedBlockConnectorPosition.getX()), staticBlockConnectorPosition.getY() + (getY() - draggedBlockConnectorPosition.getY()));
                main.mainConnector.connect(intersectingConnectorSub);
                controller.connectBlocks(main, sub, intersectingConnectorSub.getId());
                changeHeight(main.getHeight(), main);
            }
            else {
                other.setPosition(draggedBlockConnectorPosition.getX() + (other.getX() - staticBlockConnectorPosition.getX()), draggedBlockConnectorPosition.getY() + (other.getY() - staticBlockConnectorPosition.getY()));
                main.mainConnector.connect(intersectingConnectorSub);
                controller.connectBlocks(main, sub, intersectingConnectorSub.getId());
                other.changeHeight(main.getHeight(), main);
            }
        }
    }

    public void resetHeight() {
        changeHeight(getHeight(), this);
    }

    public void disconnectHeight() {
        changeHeight(-getHeight(), this);
    }

    public void disconnectMainConnector() {
        mainConnector.disconnect();
    }

    public List<GUIBlock> getConnectedBlocks() {
        List<GUIBlock> blocks = new ArrayList<>(List.of(this));

        for (GUIConnector connector : subConnectors) {
            if (connector.isConnected()) {
                blocks.addAll(connector.getConnectedGUIBlock().getConnectedBlocks());
            }
        }

        return blocks;
    }

    protected abstract void changeHeight(int heightDelta, GUIBlock previousBlock);

    private GUIConnector findCollidingConnector(List<GUIConnector> subConnectors, GUIConnector mainConnector) {

        if (mainConnector.isConnected()) {
            return null;
        }

        for (GUIConnector connector : subConnectors) {
            if (!connector.isConnected() && connector.getCollisionCircle().intersects(mainConnector.getCollisionCircle())) {
                return connector;
            }
        }
        
        return null;
    }

    protected abstract void setShapes();
}
