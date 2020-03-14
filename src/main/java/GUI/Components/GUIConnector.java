package GUI.Components;

import Controllers.ConnectionController;
import GUI.CollisionShapes.CollisionCircle;
import GUI.Blocks.GUIBlock;

import java.awt.*;

public class GUIConnector {

    private final CollisionCircle collisionCircle;
    private final GUIBlock parentBlock;
    private GUIConnector connectedConnector;
    private String id;

    public GUIConnector(String ID, GUIBlock parentBlock, int x, int y, Color color) {
        this.id = ID;
        collisionCircle = new CollisionCircle(x, y, 10, color);
        this.parentBlock = parentBlock;
    }

    public CollisionCircle getCollisionCircle() {
        return collisionCircle;
    }

    public String getId() {
        return id;
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

    public void connect(GUIConnector other, ConnectionController controller) {

        if (!controller.isValidConnection(this.parentBlock, other.parentBlock, other.id)) {
            return;
        }
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
