package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;
import java.util.zip.CheckedInputStream;

public class GUICavityBlock extends GUIBlock {

    private int cavityHeight, cavityUpHeight, cavityDownHeight;
    private CollisionRectangle cavityRectangle, cavityRectangleUnder;
    private GUIConnector cavityConnector;

    public GUICavityBlock(String ID, int x, int y) {
        super(ID,x, y);
    }

    @Override
    protected void addHeight(int height, GUIBlock previousBlock) {
        if (cavityConnector.isConnected() && cavityConnector.getConnectedGUIBlock().equals(previousBlock)) {
            increaseCavityHeight(height);
        }
    }

    @Override
    protected void setShapes() {

        int cavityWidth = 10;
        int width = 100 + cavityWidth;
        cavityUpHeight = 30;
        cavityDownHeight = 30;
        height = cavityUpHeight + cavityDownHeight;

        cavityRectangle = new CollisionRectangle(0, cavityUpHeight, cavityWidth, 0, 0, Color.white);
        cavityRectangleUnder = new CollisionRectangle(0, cavityUpHeight, width, cavityDownHeight, 0, Color.white);

        blockRectangles.add(new CollisionRectangle(0, 0, width, cavityUpHeight, 0, Color.white));
        blockRectangles.add(cavityRectangle);
        blockRectangles.add(cavityRectangleUnder);

        mainConnector = new GUIConnector(this, width / 2, 0, Color.blue);
        cavityConnector = new GUIConnector(this, (width + cavityWidth) / 2, cavityUpHeight, Color.red);
        subConnectors.add(cavityConnector);
        subConnectors.add(new GUIConnector(this, width, cavityUpHeight / 2, Color.red));
        //subConnectors.add(new GUIConnector(this, width / 2, cavityUpHeight + cavityDownHeight + cavityHeight, Color.red));
    }

    private void increaseCavityHeight(int increasement) {
        setNewCavityHeight(cavityUpHeight + increasement);
        if (cavityConnector.isConnected()) {
            // TODO
        }
    }

    private void decreaseCavityHeight(int decreasement) {
        setNewCavityHeight(cavityUpHeight - decreasement);
        if (cavityConnector.isConnected()) {
            // TODO
        }
    }

    private void setNewCavityHeight(int newCavityHeight) {
        cavityHeight = newCavityHeight;
        height = cavityUpHeight + cavityHeight + cavityDownHeight;
        cavityRectangle.setHeight(cavityHeight);
        cavityRectangleUnder.setY(getY() + cavityHeight);
    }
}
