package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

public class GUIFunctionalityBlock extends GUIBlock {

    public GUIFunctionalityBlock(String ID, int x, int y) {
        super(ID, x, y);
    }

    @Override
    protected void addHeight(int height, GUIBlock previousBlock) {
        if (mainConnector.isConnected()) {
            mainConnector.getConnectedGUIBlock().addHeight(height, this);
        }
    }
  
    @Override
    protected void setShapes() {

        height = 50;
        int width = 100;

        blockRectangles.add(new CollisionRectangle(0, 0, width, height, 0, Color.white));
        mainConnector = new GUIConnector(this, width / 2, 0, Color.blue);
        subConnectors.add(new GUIConnector(this, width / 2, height, Color.red));
    }
}
