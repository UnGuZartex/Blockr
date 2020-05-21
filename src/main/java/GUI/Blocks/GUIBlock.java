package GUI.Blocks;

import Controllers.ControllerClasses.BlockHandlerController;
import Controllers.ControllerClasses.ConnectionController;
import GUI.CollisionShapes.CollisionCircle;
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
     * Set the color of this block.
     *
     * @param color The new color for this block.
     *
     * @post Every rectangle in this block has the given color.
     */
    public void setColor(Color color) {
        if (!terminated) {
            for (CollisionRectangle rectangle : blockRectangles) {
                rectangle.setColor(color);
            }
        }
    }

    /**
     * Set the position of this block.
     *
     * @param x The new x coordinate for this block.
     * @param y The new y coordinate for this block.
     *
     * @post The coordinates of this block are set to the given coordinates.
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
     * TODO commentaar
     * @param controller
     */
    public void removeInBetween(ConnectionController connectionController, BlockHandlerController blockhandlerController) {
        if (mainConnector != null) {
            GUIBlock upperBlock = mainConnector.getConnectedGUIBlock();
            GUIBlock downBlock = subConnectors.get(0).getConnectedGUIBlock();

            if (downBlock != null) {
                downBlock.disconnectMainConnector();
            }

            if (upperBlock != null) {
                changeHeight(-height, this);
                GUIConnector sub = mainConnector.getConnectedConnector();
                int subIndex = upperBlock.getConnectorIndex(sub);
                disconnectMainConnector();

                if (downBlock != null && connectionController.isValidConnection(downBlock, upperBlock, subIndex)) {
                    downBlock.setPosition(getX(), getY());
                    downBlock.mainConnector.connect(sub);
                    connectionController.connectBlocks(downBlock, upperBlock, subIndex);
                }
            }

            if (downBlock != null) {
                blockhandlerController.addExistingBlockAsProgram(downBlock);
            }
        }
    }

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
     * Checks whether any connector and sub connector of this block
     * collide with the given block.
     *
     * @param other The other block to check collision with.
     *
     * @return True if and only if the two blocks have colliding connectors.
     */
    public boolean intersectsWithConnector(GUIBlock other) {
        return findValidSubConnector(subConnectors, other.mainConnector) != null || findValidSubConnector(other.subConnectors, mainConnector) != null;
    }

    /**
     * The current dragged block or set of blocks this block is in is being connected with a given
     * static block, if possible.
     *
     * @param other The given static block.
     *
     * @effect The height of this block and the static block and the connected set of blocks is changed accordingly.
     * @effect The position of this block set is changed accordingly to the type of completed connection.
     * @effect The position of the static block set is changed accordingly to the type of completed connection.
     * @effect A valid colliding connector of this block is connected to a valid colliding connector
     *         of the static block, if possible.
     *
     * @throws IllegalArgumentException
     *         when the given block does not have a colliding connector.
     */
    public void connectWithStaticBlock(GUIBlock other, ConnectionController connectionController) throws IllegalArgumentException {
        if (!terminated) {
            GUIConnector intersectingConnectorSub;
            Position staticBlockConnectorPosition, draggedBlockConnectorPosition;
            GUIBlock main, sub;

            if ((intersectingConnectorSub = findValidSubConnector(other.subConnectors, mainConnector)) != null) {
                main = this;
                sub = other;
                draggedBlockConnectorPosition = main.mainConnector.getPosition();
                staticBlockConnectorPosition = intersectingConnectorSub.getPosition();
            } else if ((intersectingConnectorSub = findValidSubConnector(subConnectors, other.mainConnector)) != null) {
                main = other;
                sub = this;
                staticBlockConnectorPosition = main.mainConnector.getPosition();
                draggedBlockConnectorPosition = intersectingConnectorSub.getPosition();
            } else {
                throw new IllegalArgumentException("Given block does not have a colliding connector!");
            }

            if (connectionController.isValidConnection(main, sub, sub.getConnectorIndex(intersectingConnectorSub))) {
                GUIBlock toChange = other;
                int x = draggedBlockConnectorPosition.getX() + (toChange.getX() - staticBlockConnectorPosition.getX());
                int y = draggedBlockConnectorPosition.getY() + (toChange.getY() - staticBlockConnectorPosition.getY());
                if (!mainConnector.isConnected()) {
                    toChange = this;
                    x = staticBlockConnectorPosition.getX() + (getX() - draggedBlockConnectorPosition.getX());
                    y = staticBlockConnectorPosition.getY() + (getY() - draggedBlockConnectorPosition.getY());
                }
                toChange.setPosition(x, y);
                main.mainConnector.connect(intersectingConnectorSub);
                connectionController.connectBlocks(main, sub, sub.getConnectorIndex(intersectingConnectorSub));
                toChange.changeHeight(main.getTotalHeight(), main);
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
    protected abstract void changeHeight(int heightDelta, GUIBlock previousBlock);

    /**
     * Set the shapes of this block.
     */
    protected abstract void setShapes();

    /**
     * Check whether any of the given sub connectors can connect with the main connector.
     *
     * @param subConnectors The given sub connectors.
     * @param mainConnector The given main connector.
     *
     * @return The sub connector which can connect with the given main connector. If no
     *         such connector is found, null is returned.
     */
    private GUIConnector findValidSubConnector(List<GUIConnector> subConnectors, GUIConnector mainConnector) {
        for (GUIConnector connector : subConnectors) {
            if (connector.canConnectWith(mainConnector)) {
                return connector;
            }
        }
        return null;
    }

    /**
     * TODO comments
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * TODO comments
     * @return
     */
    public void terminate() {
        this.terminated = true;
    }

    /**
     * TODO comments
     * @return
     */
    public boolean isTerminated() {
        return terminated;
    }

    /**
     * TODO comments
     * @return
     */
    @Override
    public int compareTo(GUIBlock other) {
        int comparison = Integer.compare(other.priority, this.priority);
        comparison = (comparison == 0) ? Integer.compare(this.getY(), other.getY()) : comparison;
        comparison = (comparison == 0) ? Integer.compare(this.getX(), other.getX()) : comparison;
        return comparison;
    }
}
