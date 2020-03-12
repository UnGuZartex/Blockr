package GUI.Components;

import GUI.BlockShape.CollisionCircle;

import java.awt.*;

public class GUIConnector {

    private CollisionCircle collisionCircle;
    private GUIBlock parentBlock;
    private GUIConnector connectedConnector;
    private int id;

    public GUIConnector(GUIBlock parentBlock, int x, int y, Color color) {
        collisionCircle = new CollisionCircle(x, y, 10, 0, color);
        this.parentBlock = parentBlock;
    }

    public CollisionCircle getCollisionCircle() {
        return collisionCircle;
    }

    public int getId() {
        return id;
    }

    public void setParentBlock(GUIBlock parentBlock) {
        this.parentBlock = parentBlock;
    }

    public GUIConnector getConnectedConnector() {
        return connectedConnector;
    }

    public boolean isConnected() {
        return connectedConnector != null;
    }

    private void connectSlave(GUIConnector other) {

        if (other == null) {
            // TODO
        }

        if (isConnected()) {
            // TODO
        }

        connectedConnector = other;
    }

    public void disconnect() {
        if (connectedConnector != null) {
            GUIConnector connectorTemp = connectedConnector;
            connectedConnector = null;
            connectorTemp.disconnect();
        }
    }

    public void connect(GUIConnector other) {

        // TODO moet deze check?
        if (other == null) {
            throw new IllegalArgumentException("todo");
        }

        if (isConnected()) {
            throw new IllegalStateException("todo");
        }

        if (other.isConnected()) {
            throw new IllegalStateException("todo");
        }

        connectedConnector = other;
        other.connectSlave(this);
    }

    public GUIBlock getParentBlock() {
        return parentBlock;
    }

    public GUIBlock getConnectedGUIBlock() {
        return connectedConnector.getParentBlock();
    }
}
