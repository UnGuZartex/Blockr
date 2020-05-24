package GUI.Components;

import GUI.Blocks.GUIBlock;
import GUI.CollisionShapes.CollisionCircle;
import Utility.Position;

import java.awt.*;

/**
 * A class used as a GUI connector to connect GUI Blocks visually.
 *
 * @invar A GUI connector must have a valid parent block at all time.
 *        | isValidParentBlock(parentBlock)
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
     *
     * @throws IllegalArgumentException
     *         When the given parent block is invalid.
     */
    public GUIConnector(GUIBlock parentBlock, int x, int y, Color color) throws IllegalArgumentException {
        if (!isValidParentBlock(parentBlock)) {
            throw new IllegalArgumentException("The given parent block is not valid!");
        }
        collisionCircle = new CollisionCircle(x, y, DEFAULT_CIRCLE_RADIUS, color);
        this.parentBlock = parentBlock;
    }

    /**
     * Checks whether or not the given block is a valid parent block.
     *
     * @param block The block to check.
     *
     * @return True if and only if the given block is not null.
     */
    public static boolean isValidParentBlock(GUIBlock block) {
        return block != null;
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
     * Get the connector connected to this connector.
     *
     * @return the connector connected to this connector.
     */
    public GUIConnector getConnectedConnector() {
        return connectedConnector;
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
     * Get the collision circle of this GUI connector.
     *
     * @return The collision circle of this GUI connector.
     */
    public CollisionCircle getCollisionCircle() {
        return collisionCircle;
    }

    /**
     * Return the position of this GUI connector.
     *
     * @return The position of this GUI connector.
     */
    public Position getPosition() {
        return collisionCircle.getPosition();
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
     *         If this connector can't connect with the given connector.
     */
    public void connect(GUIConnector other) throws IllegalArgumentException {
        if (!canConnectWith(other)) {
            throw new IllegalArgumentException("Given connector can not connect with this connector!");
        }
        this.alignWith(other);
        connectedConnector = other;
        other.connectAsSlave(this);
        parentBlock.changeHeight(parentBlock.getTotalHeight(), parentBlock);
    }

    /**
     * Paint this connector.
     *
     * @param g The graphics to paint this connector with.
     *
     * @effect The collision circle is painted.
     */
    public void paint(Graphics g) {
        collisionCircle.paint(g);
    }

    /**
     * Check whether or not this connector can connect with the given connector.
     *
     * @param other The connector to check compatibility off.
     *
     * @return True if and only if the given connector is effective, this and the given connector
     *         isn't connected yet and the collision circles intersect.
     */
    public boolean canConnectWith(GUIConnector other) {
        return other != null && !this.isConnected()
                && !other.isConnected() && this.collisionCircle.intersects(other.collisionCircle);
    }

    /**
     * Translate this connector with the given coordinates.
     *
     * @param x The x coordinate to translate with.
     * @param y The y coordinate to translate with.
     * @param propagate Whether or not the translation should propagate to the connected block.
     *
     * @effect The collision circle is translated with the given amount.
     * @effect If the translation should propagate, the connected block is also translated.
     */
    public void translate(int x, int y, boolean propagate) {
        collisionCircle.translate(x, y);

        if (propagate && isConnected()) {
            getConnectedGUIBlock().translate(x, y);
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
    private void connectAsSlave(GUIConnector other) throws IllegalArgumentException, IllegalStateException {

        if (other == null) {
            throw new IllegalArgumentException("The given connector is null!");
        }

        if (isConnected()) {
            throw new IllegalStateException("This connector is already connected!");
        }

        connectedConnector = other;
    }

    /**
     * Align this connector with the given connector.
     *
     * @param other The connector to align with.
     *
     * @effect The parent block is translated such that this connector and the given
     *         connector align.
     */
    private void alignWith(GUIConnector other) {
        int deltaX = other.collisionCircle.getX() - this.collisionCircle.getX();
        int deltaY = other.collisionCircle.getY() - this.collisionCircle.getY();
        parentBlock.translate(deltaX, deltaY);
    }
}
