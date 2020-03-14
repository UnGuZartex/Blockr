package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

public class GUIFunctionalityBlock extends GUIBlock {

    private GUIConnector lowerSubConnector;

    public GUIFunctionalityBlock(String name, String id, int x, int y) {
        super(name, id, x, y);
    }

    @Override
    protected void changeHeight(int heightDelta, GUIBlock previousBlock) {
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

        height = 50;
        int width = 100;

        blockRectangles.add(new CollisionRectangle(0, 0, width, height, Color.white));
        mainConnector = new GUIConnector("MAIN", this, width / 2, 0, Color.blue);
        lowerSubConnector = new GUIConnector( "SUB_1", this, width / 2, height, Color.red);
        subConnectors.add(lowerSubConnector);
    }
}
