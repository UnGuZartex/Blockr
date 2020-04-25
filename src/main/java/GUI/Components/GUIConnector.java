package GUI.Components;

import GUI.CollisionShapes.CollisionCircle;
import GUI.Blocks.GUIBlock;
import System.BlockStructure.Connectors.SubConnector;

import java.awt.*;

/**
 * A class used as a GUI connector to connect GUI Blocks visually.
 *
 * @author Alpha-team
 */
public class GUIConnector {

    /**
     * Variable referring to the collision circle of this connector.
     */
    private final CollisionCircle collisionCircle;

    /**
     * Variable referring to the parent block of this connector.
     */
    private final GUIBlock parentBlock;

    /**
     * Variable referring to the connected connector to this connector.
     */
    private GUIConnector connectedConnector;

    /**
     * Variable referring to the default radius of a connector.
     */
    private static final int DEFAULT_CIRCLE_RADIUS = 10;

    /**
     * Initialise a new GUI connector, parent block, coordinates and color.
     *
     * @param parentBlock The parent block for this GUI connector.
     * @param x The x coordinate for this GUI connector.
     * @param y The y coordinate for this GUI connector.
     * @param color The color for this GUI connector.
     *
     * @effect The collision circle is set to a new collision circle with the given
     *         coordinates and the default radius and the given color.
     *
     * @post The parent block of this connector is set to the given connector.
     */
    public GUIConnector(GUIBlock parentBlock, int x, int y, Color color) {
        collisionCircle = new CollisionCircle(x, y, DEFAULT_CIRCLE_RADIUS, color);
        this.parentBlock = parentBlock;
    }

    /**
     * Get the collision circle of this GUI connector.
     *
     * @return The collision circle of this GUI connector.
     */
    public CollisionCircle getCollisionCircle() {
        return collisionCircle;
    }

    /**
     * Checks whether this GUI connector is connected.
     *
     * @return True if and only if this GUI connector has a connected connector.
     */
    public boolean isConnected() {
        return connectedConnector != null;
    }

    /**
     * Disconnect this connector from it's connected connector.
     *
     * @post This GUI connector is no longer connected.
     * @post The connected connector is no longer connected.
     */
    public void disconnect() {
        if (connectedConnector != null) {
            GUIConnector connectorTemp = connectedConnector;
            connectedConnector = null;
            connectorTemp.disconnect();
        }
    }

    /**
     * Connects this GUI connector to the given GUI connector.
     *
     * @param other The other connector to connect.
     *
     * @post This connector is connected to the given connector.
     * @post The given connector is connected to this connector.
     *
     * @throws IllegalArgumentException
     *         If the given connector is not effective.
     * @throws IllegalStateException
     *         If this connector is already connected.
     * @throws IllegalArgumentException
     *         If the given connector is already connected.
     */
    public void connect(GUIConnector other) {

        if (other == null) {
            throw new IllegalArgumentException("Given connector is null!");
        }

        if (isConnected()) {
            throw new IllegalStateException("This connector is already connected!");
        }

        if (other.isConnected()) {
            throw new IllegalArgumentException("The given connector is already connected!");
        }

        connectedConnector = other;
        other.connectAsSlave(this);
    }

    /**
     * Get the parent block of this GUI connector.
     *
     * @return The parent block of this GUI connector.
     */
    public GUIBlock getParentBlock() {
        return parentBlock;
    }

    /**
     * Get the connected parent block to this GUI Connector.
     *
     * @return The parent block of the connected connector.
     */
    public GUIBlock getConnectedGUIBlock() {
        if (isConnected()) {
            return connectedConnector.getParentBlock();
        } else {
            return null;
        }
    }

    /**
     * Connect the given connector as a slave connector.
     *
     * @param other The connector to connect.
     *
     * @post This connector is connected to the given connector.
     *
     * @throws IllegalArgumentException
     *         If the given connector is not effective.
     * @throws IllegalStateException
     *         If this connector is already connected.
     */
    private void connectAsSlave(GUIConnector other) {

        if (other == null) {
            throw new IllegalArgumentException("The given connector is null!");
        }

        if (isConnected()) {
            throw new IllegalStateException("This connector is already connected!");
        }

        connectedConnector = other;
    }
}
