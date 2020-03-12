package GUI.Components;

import GUI.BlockShape.CollisionCircle;

import java.awt.*;

public class GUIConnector {

    private CollisionCircle collisionCircle;
    private GUIBlock connectedBlock;
    private GUIConnector connectedConnector;
    private int id;

    public GUIConnector(GUIBlock connectedBlock, int x, int y, Color color) {
        collisionCircle = new CollisionCircle(x, y, 10, 0, color);
        this.connectedBlock = connectedBlock;
    }

    public CollisionCircle getCollisionCircle() {
        return collisionCircle;
    }

    public int getId() {
        return id;
    }

    private boolean isConnected() {
        return connectedConnector != null;
    }

    public void connect(GUIConnector connector) {

        // TODO moet deze check?
        if (connector == null) {
            throw new IllegalArgumentException("todo");
        }

        if (isConnected()) {
            throw new IllegalStateException("todo");
        }
    }
}
