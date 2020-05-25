package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.util.ArrayList;

public abstract class GUISimpleCavityBlock extends GUIBlock {

    /**
     * Variables referring to the default lengths for a cavity block.
     */
    protected static final int DEFAULT_CAVITY_WIDTH = 10, DEFAULT_CAVITY_UP_HEIGHT = 30,
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
    protected GUIConnector cavityConnector;

    /**
     * Initialise a new cavity block with given name and coordinates.
     *
     * @param name The name for this cavity block.
     * @param x The x coordinate for this block.
     * @param y The y coordinate for this block.
     *
     * @effect Calls the super constructor with given parameters.
     */
    protected GUISimpleCavityBlock(String name, int x, int y) {
        super(name, x, y);
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
     */
    @Override
    public void changeHeight(int heightDelta, GUIBlock previousBlock) {
        if (cavityConnector.isConnected() && cavityConnector.getConnectedGUIBlock().equals(previousBlock)) {
            changeCavityHeight(heightDelta);
        }
    }

    /**
     * Set the shapes of this cavity block.
     *
     * @post The width is set to the default with plus the default cavity width.
     * @post The cavity height up is set to the default value.
     * @post The cavity height down is set to the default value.
     * @post The height is set to the cavity up height plus the cavity down height.
     *
     * @effect A new rectangle for the upper part is initialised and
     *         added to the collision rectangles.
     * @effect A new rectangle for the lower part is initialised and
     *         added to the collision rectangles.
     * @effect A new rectangle for the cavity is initialised and added
     *         to the collision rectangles.
     * @effect The connectors are set.
     */
    @Override
    protected void setShapes() {
        width = DEFAULT_WIDTH + DEFAULT_CAVITY_WIDTH;
        cavityUpHeight = DEFAULT_CAVITY_UP_HEIGHT;
        cavityDownHeight = DEFAULT_CAVITY_DOWN_HEIGHT;
        height = cavityUpHeight + cavityDownHeight;

        cavityRectangle = new CollisionRectangle(0, cavityUpHeight, DEFAULT_CAVITY_WIDTH, 0, DEFAULT_BLOCK_COLOR);
        cavityRectangleUnder = new CollisionRectangle(0, cavityUpHeight, width, cavityDownHeight, DEFAULT_BLOCK_COLOR);

        blockRectangles.add(new CollisionRectangle(0, 0, width, cavityUpHeight, DEFAULT_BLOCK_COLOR));
        blockRectangles.add(cavityRectangle);
        blockRectangles.add(cavityRectangleUnder);

        setConnectors();
    }

    /**
     * Set the connectors of this cavity block.
     *
     * @effect The cavity sub connector is set to a new connector which is between the 2 parts of the block.
     * @effect The sub connectors list is set to a new list with the cavity sub connector.
     */
    protected void setConnectors() {
        cavityConnector = new GUIConnector(this, (width + DEFAULT_CAVITY_WIDTH) / 2, cavityUpHeight, DEFAULT_SUB_CONNECTOR_COLOR);
        subConnectors = new ArrayList<>();
        subConnectors.add(cavityConnector);
    }

    /**
     * Change the cavity height of this cavity block with the given height difference.
     *
     * @param heightDelta The given height difference.
     *
     * @effect Set the cavity height to the newly calculated height.
     *
     * @throws IllegalArgumentException
     *         when the given height delta is invalid.
     */
    protected void changeCavityHeight(int heightDelta) throws IllegalArgumentException {
        if (cavityHeight + heightDelta < 0) {
            throw new IllegalArgumentException("The given height delta is invalid, cavity height can't be < 0!");
        }

        setNewCavityHeight(cavityHeight + heightDelta);
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
     */
    protected void setNewCavityHeight(int newCavityHeight) {
        cavityHeight = newCavityHeight;
        height = cavityUpHeight + cavityHeight + cavityDownHeight;
        cavityRectangle.setHeight(cavityHeight);
        cavityRectangleUnder.setY(getY() + cavityUpHeight + cavityHeight);
    }
}
