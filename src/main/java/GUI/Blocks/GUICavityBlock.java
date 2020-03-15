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
    private int cavityHeight, cavityUpHeight, cavityDownHeight;
    /**
     * Variables referring to the collision rectangles for this block.
     */
    private CollisionRectangle cavityRectangle, cavityRectangleUnder;
    /**
     * Variables referring to the connectors of this cavity block.
     */
    private GUIConnector cavityConnector, conditionalConnector, lowerSubConnector;

    /**
     * Initialise a new cavity block with given name, id and coordinates.
     *
     * @param name The name for this cavity block.
     * @param id The id for this cavity block.
     * @param x The x coordinate for this block.
     * @param y The y coordinate for this block.
     *
     * @effect Calls the super constructor with given parameters.
     */
    public GUICavityBlock(String name, String id, int x, int y) {
        super(name, id, x, y);
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

        if (conditionalConnector.isConnected()) {
            conditionalConnector.getConnectedGUIBlock().setColor(color);
        }
    }

    @Override
    protected void changeHeight(int heightDelta, GUIBlock previousBlock) {

        if (cavityConnector.isConnected() && cavityConnector.getConnectedGUIBlock().equals(previousBlock)) {
            changeCavityHeight(heightDelta);
        }

        if (mainConnector.isConnected()) {
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
    public int getHeight() {
        if (lowerSubConnector.isConnected()) {
            return height + lowerSubConnector.getConnectedGUIBlock().getHeight();
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

        mainConnector = new GUIConnector("MAIN", this, (width - DEFAULT_CAVITY_WIDTH) / 2, 0, Color.blue);
        cavityConnector = new GUIConnector("CAVITY", this, (width + DEFAULT_CAVITY_WIDTH) / 2, cavityUpHeight, Color.red);
        lowerSubConnector = new GUIConnector("SUB_1",this, (width - DEFAULT_CAVITY_WIDTH) / 2, cavityUpHeight+cavityDownHeight+cavityHeight, Color.red);
        conditionalConnector = new GUIConnector("CONDITIONAL", this, width, cavityUpHeight / 2, Color.red);
        subConnectors.add(cavityConnector);
        subConnectors.add(lowerSubConnector);
        subConnectors.add(conditionalConnector);
    }

    // TODO doc
    private void changeCavityHeight(int heightDelta) throws IllegalArgumentException {
        if (cavityHeight + heightDelta < 0) {
            throw new IllegalArgumentException("Invalid height delta, cavity height can't be < 0!");
        }

        setNewCavityHeight(cavityHeight + heightDelta);

        if (lowerSubConnector.isConnected()) {
            lowerSubConnector.getConnectedGUIBlock().translate(0, heightDelta);
        }
    }

    // TODO doc
    private void setNewCavityHeight(int newCavityHeight) {
        cavityHeight = newCavityHeight;
        height = cavityUpHeight + cavityHeight + cavityDownHeight;
        cavityRectangle.setHeight(cavityHeight);
        cavityRectangleUnder.setY(getY() + cavityUpHeight + cavityHeight);
        lowerSubConnector.getCollisionCircle().setY(cavityRectangleUnder.getY() + cavityDownHeight);
    }
}
