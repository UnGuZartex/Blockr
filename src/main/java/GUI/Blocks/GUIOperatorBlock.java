package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

public class GUIOperatorBlock extends GUIBlock {
    public GUIOperatorBlock(String name, int x, int y) {
        super(name, x, y);
}

    @Override
    protected void setShapes() {

        int height = 30;
        int width = 40;

        blockRectangles.add(new CollisionRectangle(0, 0, width, height, 0, Color.white));
        mainConnector = new GUIConnector("MAIN",this, 0, height / 2, Color.blue);
        subConnectors.add(new GUIConnector("SUB_1", this, width, height / 2, Color.red));
    }
}
