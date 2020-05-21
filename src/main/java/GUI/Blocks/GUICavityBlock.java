package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

public class GUICavityBlock extends GUIBlock {

    /**
     * Variables referring to the default lengths for a cavity block.
     */
    public static final int DEFAULT_CAVITY_WIDTH = 10, DEFAULT_CAVITY_UP_HEIGHT = 30,
            DEFAULT_CAVITY_DOWN_HEIGHT = 30, DEFAULT_WIDTH = 100;
    /**
     * Variables referring to the height of the cavity.
     */
    protected int cavityHeight, cavityUpHeight, cavityDownHeight;
    /**
     * Variables referring to the collision rectangles for this block.
     */
    protected CollisionRectangle cavityRectangle, cavityRectangleUnder;
    /**
     * Variables referring to the connectors of this cavity block.
     */
    protected GUIConnector cavityConnector, conditionalConnector, lowerSubConnector;

    /**
     * Initialise a new cavity block with given name and coordinates.
     *
     * @param name The name for this cavity block.
     * @param x The x coordinate for this block.
     * @param y The y coordinate for this block.
     *
     * @effect Calls the super constructor with given parameters.
     */
    public GUICavityBlock(String name, int x, int y) {
        super(name, x, y);
    }

    /**
     * Set the color of this cavity block.
     *
     * @param color The new color for this block.
     *
     * @post The color of this block is set to the given color.
     *
     * @effect The conditional is set to the given color, if any exists.
     */
    @Override
    public void setColor(Color color) {
        super.setColor(color);

        if (conditionalConnector != null && conditionalConnector.isConnected()) {
            conditionalConnector.getConnectedGUIBlock().setColor(color);
        }
    }

    /**
     * Change the height of this block if possible and affect all connected
     * blocks accordingly.
     *
     * @param heightDelta The given height difference.
     * @param previousBlock The previous block that called this method.
     *
     * @effect If the cavity connector is connected and the method call came from that connected block,
     *         then the cavity changes height based on the given height delta.
     * @effect If the main connector is connected to a block, this method is called on that connected block to
     *         further propagate the height change call.
     */
    @Override
    public void changeHeight(int heightDelta, GUIBlock previousBlock) {

        if (cavityConnector.isConnected() && cavityConnector.getConnectedGUIBlock().equals(previousBlock)) {
            System.out.println("cavity changed " + heightDelta);
            changeCavityHeight(heightDelta);
        }

        if (mainConnector != null && mainConnector.isConnected()) {
            mainConnector.getConnectedGUIBlock().changeHeight(heightDelta, this);
        }
    }

    /**
     * Return the height of this cavity connector.
     *
     * @return The height of this cavity connector summed with the height of the
     *         connected block to the lower sub connector, if any is connected.
     */
    @Override
    public int getTotalHeight() {
        if (lowerSubConnector != null && lowerSubConnector.isConnected()) {
            return height + lowerSubConnector.getConnectedGUIBlock().getTotalHeight();
        }

        return height;
    }

    /**
     * Set the shapes of this cavity block.
     *
     * @effect A new rectangle for the upper part is initialised and
     *         added to the collision rectangles.
     * @effect A new rectangle for the lower part is initialised and
     *         added to the collision rectangles.
     * @effect A new rectangle for the cavity is initialised and added
     *         to the collision rectangles.
     * @effect A new sub connector for the cavity is initialised and added
     *         to the sub connectors.
     * @effect A new sub connector for the lower part is initialised and added
     *         to the sub connectors.
     * @effect A new sub connector for the conditional is initialised and added
     *         to the sub connectors.
     */
    @Override
    protected void setShapes() {
        width = DEFAULT_WIDTH + DEFAULT_CAVITY_WIDTH;
        cavityUpHeight = DEFAULT_CAVITY_UP_HEIGHT;
        cavityDownHeight = DEFAULT_CAVITY_DOWN_HEIGHT;
        height = cavityUpHeight + cavityDownHeight;

        cavityRectangle = new CollisionRectangle(0, cavityUpHeight, DEFAULT_CAVITY_WIDTH, 0, Color.white);
        cavityRectangleUnder = new CollisionRectangle(0, cavityUpHeight, width, cavityDownHeight, Color.white);

        blockRectangles.add(new CollisionRectangle(0, 0, width, cavityUpHeight, Color.white));
        blockRectangles.add(cavityRectangle);
        blockRectangles.add(cavityRectangleUnder);

        setConnectors();
    }

    /**
     * Clone this gui block and return the clone.
     *
     * @return A clone of this gui block.
     */
    @Override
    public GUIBlock clone() {
        return new GUICavityBlock(name, x, y);
    }

    /**
     * Change the cavity height of this cavity block with the given height difference.
     *
     * @param heightDelta The given height difference.
     *
     * @effect Set the cavity height to the newly calculated height.
     * @effect If the lower sub connector is connected, the blocks connected to that connector
     *         are translated to correct their position after the cavity height change.
     *
     * @throws IllegalArgumentException
     *         when the given height delta is invalid.
     */
    private void changeCavityHeight(int heightDelta) throws IllegalArgumentException {
        if (cavityHeight + heightDelta < 0) {
            throw new IllegalArgumentException("The given height delta is invalid, cavity height can't be < 0!");
        }

        setNewCavityHeight(cavityHeight + heightDelta);

        if (lowerSubConnector != null && lowerSubConnector.isConnected()) {
            lowerSubConnector.getConnectedGUIBlock().translate(0, heightDelta);
        }
    }

    /**
     * Set the cavity height of this cavity block to the given cavity height and change
     * other block properties accordingly.
     *
     * @param newCavityHeight The given cavity height.
     *
     * @post The cavity height is set to the given cavity height.
     * @post The block height is set the correct value, factoring in the new cavity height.
     *
     * @effect The height of the rectangle representing the cavity is set to the given cavity height.
     * @effect The position of the lower cavity rectangle is changed accordingly.
     * @effect The position of the lower sub connector is changed accordingly.
     */
    private void setNewCavityHeight(int newCavityHeight) {
        cavityHeight = newCavityHeight;
        height = cavityUpHeight + cavityHeight + cavityDownHeight;
        cavityRectangle.setHeight(cavityHeight);
        cavityRectangleUnder.setY(getY() + cavityUpHeight + cavityHeight);
        if (lowerSubConnector != null) {
            lowerSubConnector.getCollisionCircle().setY(cavityRectangleUnder.getY() + cavityDownHeight);
        }
    }

    protected void setConnectors() {
        mainConnector = new GUIConnector(this, (width - DEFAULT_CAVITY_WIDTH) / 2, 0, Color.blue);
        cavityConnector = new GUIConnector(this, (width + DEFAULT_CAVITY_WIDTH) / 2, cavityUpHeight, Color.red);
        lowerSubConnector = new GUIConnector(this, (width - DEFAULT_CAVITY_WIDTH) / 2, cavityUpHeight+cavityDownHeight+cavityHeight, Color.red);
        conditionalConnector = new GUIConnector(this, width, cavityUpHeight / 2, Color.red);
        subConnectors.add(lowerSubConnector);
        subConnectors.add(cavityConnector);
        subConnectors.add(conditionalConnector);
    }
}
