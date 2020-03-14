package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

public class GUICavityBlock extends GUIBlock {

    private int cavityHeight, cavityUpHeight, cavityDownHeight;
    private CollisionRectangle cavityRectangle, cavityRectangleUnder;
    private GUIConnector cavityConnector, conditionalConnector, lowerSubConnector;

    public GUICavityBlock(String name, String id, int x, int y) {
        super(name, id, x, y);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        conditionalConnector.getConnectedGUIBlock().setColor(color);
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

    @Override
    public int getHeight() {
        if (lowerSubConnector.isConnected()) {
            return height + lowerSubConnector.getConnectedGUIBlock().getHeight();
        }

        return height;
    }

    @Override
    protected void setShapes() {

        int cavityWidth = 10;
        int width = 100 + cavityWidth;
        cavityUpHeight = 30;
        cavityDownHeight = 30;
        height = cavityUpHeight + cavityDownHeight;

        cavityRectangle = new CollisionRectangle(0, cavityUpHeight, cavityWidth, 0, Color.white);
        cavityRectangleUnder = new CollisionRectangle(0, cavityUpHeight, width, cavityDownHeight, Color.white);

        blockRectangles.add(new CollisionRectangle(0, 0, width, cavityUpHeight, Color.white));
        blockRectangles.add(cavityRectangle);
        blockRectangles.add(cavityRectangleUnder);

        mainConnector = new GUIConnector("MAIN", this, (width - cavityWidth) / 2, 0, Color.blue);
        cavityConnector = new GUIConnector("CAVITY", this, (width + cavityWidth) / 2, cavityUpHeight, Color.red);
        lowerSubConnector = new GUIConnector("SUB_1",this, (width - cavityWidth) / 2, cavityUpHeight+cavityDownHeight+cavityHeight, Color.red);
        conditionalConnector = new GUIConnector("CONDITIONAL", this, width, cavityUpHeight / 2, Color.red);
        subConnectors.add(cavityConnector);
        subConnectors.add(lowerSubConnector);
        subConnectors.add(conditionalConnector);

    }

    private void changeCavityHeight(int heightDelta) throws IllegalArgumentException {

        if (cavityHeight + heightDelta < 0) {
            throw new IllegalArgumentException("Invalid height delta, cavity height can't be < 0!");
        }

        setNewCavityHeight(cavityHeight + heightDelta);

        if (lowerSubConnector.isConnected()) {
            lowerSubConnector.getConnectedGUIBlock().translate(0, heightDelta);
        }
    }

    private void setNewCavityHeight(int newCavityHeight) {
        cavityHeight = newCavityHeight;
        height = cavityUpHeight + cavityHeight + cavityDownHeight;
        cavityRectangle.setHeight(cavityHeight);
        cavityRectangleUnder.setY(getY() + cavityUpHeight + cavityHeight);
        lowerSubConnector.getCollisionCircle().setY(cavityRectangleUnder.getY() + cavityDownHeight);
    }
}
