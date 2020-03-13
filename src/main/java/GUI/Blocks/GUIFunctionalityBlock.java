package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

public class GUIFunctionalityBlock extends GUIBlock {

    public GUIFunctionalityBlock(String name, int x, int y) {
        super(name, x, y);
    }
  
    @Override
    protected void setShapes() {
        int height = 50;
        int width = 100;

        blockRectangles.add(new CollisionRectangle(0, 0, width, height, 0, Color.white));
        mainConnector = new GUIConnector("MAIN", this, width / 2, 0, Color.blue);
        subConnectors.add(new GUIConnector( "SUB_1", this, width / 2, height, Color.red));
    }
}
