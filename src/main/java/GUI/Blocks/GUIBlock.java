package GUI.Blocks;

import Controllers.ControllerClasses.BlockHandlerController;
import Controllers.ControllerClasses.ConnectionController;
import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;
import Utility.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for representing graphical blocks.
 *
 * @author Alpha-team
 */
public abstract class GUIBlock implements IGUIBlock, Comparable<GUIBlock> {

    /**
     * Variables referring to the width, height, coordinates and priority of this block.
     */
    protected int height, width, x, y, priority;
    /**
     * Variable referring to the main connector of this block.
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
     * Variable referring to the id of this block.
     */
    protected String name;
    /**
     * Variable indicating if this block is terminated.
     */
    private boolean terminated;

    /**
     * Variable referring to the colours of blocks, and it's components.
     */
    public static final Color DEFAULT_BLOCK_COLOR = Color.WHITE;
    public static final Color DEFAULT_SUB_CONNECTOR_COLOR = Color.RED;
    public static final Color DEFAULT_MAIN_CONNECTOR_COLOR = Color.BLUE;

    /**
     * Initialise a new GUI block with given name and coordinates.
     *
     * @param name The name for this block.
     * @param x The x coordinate for this block.
     * @param y The y coordinate for this block.
     *
     * @effect The shapes of this block are set.
     * @effect The position of this block is set with given coordinates.
     *
     * @post The name of this block is set to the given name.
     */
    protected GUIBlock(String name, int x, int y) {
        this.name = name;
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
     * Return the position of this block.
     *
     * @return the position of this block.
     */
    public Position getPosition() {
        return new Position(x, y);
    }

    /**
     * Calculate the total height of the block structure this block is connected to, starting
     * from this block.
     *
     * @return the total height of the block structure this block is connected to, starting
     * from this block.
     */
    public int getTotalHeight() {
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
     * Get the name of this block.
     *
     * @return The name of this block.
     */
    public String getName() {
        return name;
    }

    /**
     * Terminate this block.
     *
     * @post This block is terminated.
     */
    public void terminate() {
        this.terminated = true;
    }

    /**
     * Check whether or not this block is terminated.
     *
     * @return True if and only if this block is terminated.
     */
    public boolean isTerminated() {
        return terminated;
    }

    /**
     * Set the color of this block.
     *
     * @param color The new color for this block.
     *
     * @post Every rectangle in this block has the given color, if this block isn't terminated.
     */
    public void setColor(Color color) {
        if (isTerminated()) return;
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
     *
     * @effect All rectangles in this block are translated to the given position.
     * @effect All sub connectors are translated to the given position.
     * @effect The main connector is translated to the given position.
     */
    public void setPosition(int x, int y) {
        int deltaX = x - this.x;
        int deltaY = y - this.y;

        // Translate this block and the underlying blocks.
        subConnectors.forEach(s -> s.translate(deltaX, deltaY, true));
        if (mainConnector != null) mainConnector.translate(deltaX, deltaY, false);
        blockRectangles.forEach(s -> s.translate(deltaX, deltaY));

        // Set the coordinates.
        this.x = x;
        this.y = y;
    }

    /**
     * Translate the position of this block with the given amount
     *
     * @param x The amount to translate horizontally.
     * @param y The amount to translate vertically.
     *
     * @effect The position is set to the current coordinates summed
     *         with the change amount.
     */
    public void translate(int x, int y) {
        setPosition(this.x + x, this.y + y);
    }

    /**
     * Return the index of the given sub connector in the sub connector list.
     *
     * @param subConnector The given sub connector.
     *
     * @return the index of the given sub connector in the sub connector list.
     */
    public int getConnectorIndex(GUIConnector subConnector) {
        return subConnectors.indexOf(subConnector);
    }

    /**
     * Remove this block from between its main connector and its sub connector below.
     *
     * @param connectionController The connection controller to execute the removal with.
     * @param blockHandlerController The block handler controller to execute the removal with.
     *
     * @pre This block is already disconnected in the system.
     *
     * @post If this block is only connected on its main connector, it is disconnected and the
     *       connected block is also disconnected.
     * @post If this block is only connected on its sub connector below, it is disconnected and the
     *       connected block is also disconnected.
     * @post If this block is connected on its main connector and sub connector below, than it is on
     *       both connectors disconnected and the block which where connected are connected onto each other.
     */
    public void removeInBetween(ConnectionController connectionController, BlockHandlerController blockHandlerController) {
        if (mainConnector != null) {
            GUIBlock prevBlock = mainConnector.getConnectedGUIBlock();
            GUIBlock nextBlock = null;
            disconnectHeight();

            if (subConnectors.size() > 0) {
                nextBlock = subConnectors.get(0).getConnectedGUIBlock();
                subConnectors.get(0).disconnect();
            }

            if (prevBlock != null) {
                GUIConnector sub = mainConnector.getConnectedConnector();
                int subIndex = prevBlock.getConnectorIndex(sub);
                disconnectMainConnector();

                if (nextBlock != null && connectionController.isValidConnection(nextBlock, prevBlock, subIndex)) {
                    nextBlock.setPosition(getX(), getY());
                    nextBlock.mainConnector.connect(sub);
                    connectionController.connectBlocks(nextBlock, prevBlock, subIndex);
                }
            }

            if (nextBlock != null) {
                blockHandlerController.addExistingBlockAsProgram(nextBlock);
            }
        }
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
        if (!terminated) {
            if (mainConnector != null) mainConnector.paint(g);
            subConnectors.forEach(s -> s.paint(g));
            blockRectangles.forEach(s -> s.paint(g));
            g.drawString(name, this.x + 2, this.y + 20);
        }
    }

    /**
     * Checks whether the given coordinates are in any rectangle.
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
     * Checks whether this block is inside the given area.
     *
     * @param area The area to check if this block is inside it.
     *
     * @return True if and only if this block is in the given area.
     */
    public boolean isInside(CollisionRectangle area) {
        return blockRectangles.stream().allMatch(area::contains);
    }

    /**
     * Checks whether any connector of this block can potentially connect with the given block.
     *
     * @param other The other block to check connection with.
     *
     * @return True if and only if the two blocks have colliding connectors that can potentially connect.
     */
    public boolean canPotentiallyConnectWith(GUIBlock other) {
        return this.findConnectableSubConnector(other.mainConnector) != null || other.findConnectableSubConnector(mainConnector) != null;
    }

    /**
     * The current dragged block or set of blocks this block is in is being connected with a given
     * static block, if possible.
     *
     * @param other The given static block.
     * @param connectionController The given connection controller.
     *
     * @effect A valid colliding connector of this block is connected to a valid colliding connector
     *         of the static block, if possible.
     * @effect The colliding connectors are connected in the system through the connection controller.
     *
     * @throws IllegalArgumentException
     *         when the given block cannot connect with this block.
     */
    public void connectWithStaticBlock(GUIBlock other, ConnectionController connectionController) throws IllegalArgumentException {
        if (!terminated) {
            if (!canPotentiallyConnectWith(other)) {
                throw new IllegalArgumentException("Given block cannot connect with this block!");
            }

            GUIConnector intersectingConnectorSub = other.findConnectableSubConnector(mainConnector);
            GUIBlock main = this, sub = other;

            if (intersectingConnectorSub == null) {
                intersectingConnectorSub = this.findConnectableSubConnector(other.mainConnector);
                main = other;
                sub = this;
            }

            if (connectionController.isValidConnection(main, sub, sub.getConnectorIndex(intersectingConnectorSub))) {
                main.mainConnector.connect(intersectingConnectorSub);
                connectionController.connectBlocks(main, sub, sub.getConnectorIndex(intersectingConnectorSub));
            }
        }
    }

    /**
     * Reset the height of the block structure connected to this block's main connector
     * as if the current block and its connected sub-blocks where added to the block structure.
     */
    public void resetHeight() {
        changeHeight(getTotalHeight(), this);
    }

    /**
     * Decrease the height of the block structure connected to this block's main connector
     * as if the current block and its connected sub-blocks where removed from the block structure.
     */
    public void disconnectHeight() {
        changeHeight(-getTotalHeight(), this);
    }

    /**
     * Disconnect the main connector of this block.
     *
     * @post The main connector is not connected anymore.
     * @post The sub connector which was connected to the main connector
     *       is not connected anymore.
     */
    public void disconnectMainConnector() {
        if (mainConnector != null) {
            mainConnector.disconnect();
        }
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
     * Get the color of this block.
     *
     * @return The color of the rectangles of this block.
     */
    public Color getColor() {
        return blockRectangles.get(0).getColor();
    }

    /**
     * Compare this block to the given block.
     *
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(GUIBlock other) {
        int comparison = Integer.compare(other.priority, this.priority);
        comparison = (comparison == 0) ? Integer.compare(this.getY(), other.getY()) : comparison;
        comparison = (comparison == 0) ? Integer.compare(this.getX(), other.getX()) : comparison;
        return comparison;
    }

    /**
     * Clone this gui block and return the clone.
     *
     * @return A clone of this gui block.
     */
    public abstract GUIBlock clone();

    /**
     * Change the height of this block if possible and affect all connected
     * blocks accordingly.
     *
     * @param heightDelta The given height difference.
     * @param previousBlock The previous block that called this method.
     */
    public abstract void changeHeight(int heightDelta, GUIBlock previousBlock);

    /**
     * Set the shapes of this block.
     */
    protected abstract void setShapes();

    /**
     * Check whether any of the sub connectors on this block can connect with the given main connector.
     *
     * @param mainConnector The given main connector.
     *
     * @return The sub connector which can connect with the given main connector. If no
     *         such connector is found, null is returned.
     */
    private GUIConnector findConnectableSubConnector(GUIConnector mainConnector) {
        for (GUIConnector connector : subConnectors) {
            if (connector.canConnectWith(mainConnector)) {
                return connector;
            }
        }

        return null;
    }
}