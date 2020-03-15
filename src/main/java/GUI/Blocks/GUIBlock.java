package GUI.Blocks;

import Controllers.ConnectionController;
import Controllers.ProgramController;
import GUI.CollisionShapes.CollisionCircle;
import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;
import Utility.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for GUI blocks.
 *
 * @author Alpha-team
 */
public abstract class GUIBlock {

    /**
     * Variables referring to the width, height and coordinates of this GUI block.
     */
    protected int height, width, x, y;
    /**
     * Variable referring to the main connnector of this block.
     */
    protected GUIConnector mainConnector;
    /**
     * Variable referring to the sub connectors of this block.
     */
    protected List<GUIConnector> subConnectors = new ArrayList<>();
    /**
     * Variable referring to the collision rectangles this block exists of.
     */
    protected List<CollisionRectangle> blockRectangles = new ArrayList<>();
    /**
     * Variables referring to the name and id of this block.
     */
    private final String name, id;

    /**
     * Initialise a new GUI block with given name, id and coordinates.
     *
     * @param name The name for this block.
     * @param id The id for this block.
     * @param x The x coordinate for this block.
     * @param y The y coordinate for this block.
     *
     * @effect The shapes of this block are set.
     * @effect The position of this block is set with given coordinates.
     *
     * @post The name of this block is set to the given name.
     * @post The id of this block is set to the given id.
     */
    protected GUIBlock(String name, String id, int x, int y) {
        this.name = name;
        this.id = id;
        setShapes();
        setPosition(x, y);
    }

    /**
     * Get the x coordinate of this block.
     *
     * @return The x coordinate of this block.
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y coordinate of this block.
     *
     * @return The y coordinate of this block.
     */
    public int getY() {
        return y;
    }

    /**
     * Get the id of this block.
     *
     * @return The id of this block.
     */
    public String getId() {
        return id;
    }

    /**
     * Get the height of this block.
     *
     * @return The height of this block.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the width of this block.
     *
     * @return The width of this block.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set the color of this block.
     *
     * @param color The new color for this block.
     *
     * @post Every rectangle in this block has the given color.
     */
    public void setColor(Color color) {
        for (CollisionRectangle rectangle : blockRectangles) {
            rectangle.setColor(color);
        }
    }

    /**
     * Set the position of this block.
     *
     * @param x The new x coordinate for this block.
     * @param y The new y coordinate for this block.
     *
     * @post The coordinates of this block are set to the given coordinates.
     * @post All rectangles in this block are translated to the given position.
     * @post All main connector is translated to the given position.
     * @post The sub connector and its connected blocks are translated.
     */
    public void setPosition(int x, int y) {
        int deltaX = x - this.x;
        int deltaY = y - this.y;
        // Translate the sub connectors
        for (GUIConnector connector: subConnectors) {
            CollisionCircle circle = connector.getCollisionCircle();
            circle.translate(deltaX, deltaY);
            if (connector.isConnected()) {
                connector.getConnectedGUIBlock().translate(deltaX, deltaY);
            }
        }
        // Translate main connector
        CollisionCircle circle = mainConnector.getCollisionCircle();
        circle.translate(deltaX, deltaY);
        // Translate the rectangles
        for (CollisionRectangle blockRectangle : blockRectangles) {
            blockRectangle.translate(deltaX, deltaY);
        }
        // Set the coordinates.
        this.x = x;
        this.y = y;
    }

    /**
     * Change the height of this block.
     *
     * @param heightDelta The height difference.
     * @param previousBlock The previous block.
     */
    protected abstract void changeHeight(int heightDelta, GUIBlock previousBlock);

    /**
     * Set the shapes of this block.
     */
    protected abstract void setShapes();

    /**
     * Translate the position of this block with the given amount
     *
     * @param x The amount to translate horizontally.
     * @param y The amount to translate vertically.
     *
     * @effect The position is set to the current coordinates summed
     *         with the change amount
     */
    public void translate(int x, int y) {
        setPosition(this.x + x, this.y + y);
    }

    /**
     * Paint this block
     *
     * @param g The graphics to paint with.
     *
     * @effect Every sub connector is painted.
     * @effect The main connector is painted.
     * @effect The collision rectangles are pained.
     * @effect The name of this block is painted.
     */
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

    /**
     * Checks whether or not the given coordinates are in any rectangle.
     *
     * @param x The x coordinate to check.
     * @param y The y coordinate to check.
     *
     * @return True if and only if the given coordinates are contained
     *         in any of the collision rectangles of this block.
     */
    public boolean contains(int x, int y) {
        return blockRectangles.stream().anyMatch(i -> i.contains(x, y));
    }

    /**
     * Checks whether or not this block is inside the given area.
     *
     * @param area The area to check if this block is inside it.
     *
     * @return True if and only if this block is in the given area.
     */
    public boolean isInside(CollisionRectangle area) {
        return blockRectangles.stream().allMatch(area::contains);
    }

    /**
     * Checks whether or not any connector and sub connector of this block
     * and the given block collide.
     *
     * @param other The other block to check collision with.
     *
     * @return True if and only if the two blocks have colliding connectors.
     */
    public boolean intersectsWithConnector(GUIBlock other) {
        return findCollidingConnector(subConnectors, other.mainConnector) != null || findCollidingConnector(other.subConnectors, mainConnector) != null;
    }

    // TODO doc
    public void connectWithStaticBlock(GUIBlock other, ProgramController Pcontroller) {

        GUIConnector intersectingConnectorSub;
        ConnectionController controller = Pcontroller.getController();
        Position staticBlockConnectorPosition, draggedBlockConnectorPosition;
        GUIBlock main, sub;

        if ((intersectingConnectorSub = findCollidingConnector(other.subConnectors, mainConnector)) != null) {
            main = this;
            sub = other;
            Pcontroller.deleteAsProgram(this);
            draggedBlockConnectorPosition = mainConnector.getCollisionCircle().getPosition();
            staticBlockConnectorPosition = intersectingConnectorSub.getCollisionCircle().getPosition();
        }
        else if ((intersectingConnectorSub = findCollidingConnector(subConnectors, other.mainConnector)) != null) {
            sub = this;
            main = other;
            Pcontroller.deleteAsProgram(other);
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
        Pcontroller.addBlockToPA(getHighest());
    }

    /**
     * Get the hightest block in the connections.
     *
     * @return This block if no block is higher, else the highest
     *         block of the block connected to the main connector.
     */
    public GUIBlock getHighest() {
        if (mainConnector.isConnected()) {
            return mainConnector.getConnectedGUIBlock().getHighest();
        }
        else {
            return this;
        }
    }

    // TODO doc
    public void resetHeight() {
        changeHeight(getHeight(), this);
    }

    // TODO doc
    public void disconnectHeight() {
        changeHeight(-getHeight(), this);
    }

    /**
     * Disconnect the main connector of this block.
     *
     * @post The main connector is not connected anymore.
     * @post The sub connector which was connected to the main connector
     *       is not connected anymore.
     */
    public void disconnectMainConnector() {
        mainConnector.disconnect();
    }

    /**
     * Get a list of all the connected blocks.
     *
     * @return A list with all the connected blocks to this block through
     *         a sub connector.
     */
    public List<GUIBlock> getConnectedBlocks() {
        List<GUIBlock> blocks = new ArrayList<>(List.of(this));
        for (GUIConnector connector : subConnectors) {
            if (connector.isConnected()) {
                blocks.addAll(connector.getConnectedGUIBlock().getConnectedBlocks());
            }
        }
        return blocks;
    }

    /**
     * Check whether or not the any sub connector collides with the main connector.
     *
     * @param subConnectors The sub connectors to check.
     * @param mainConnector The main connector to check.
     *
     * @return The sub connector which collides with the given main connector. If no
     *         such connector is found or if the main connector is already connected,
     *         then null is returned.
     */
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
}
