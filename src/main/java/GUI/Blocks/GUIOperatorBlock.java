package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

public class GUIOperatorBlock extends GUIBlock {
    public GUIOperatorBlock(String name, int x, int y) {
        super(name, x, y);
}

    @Override
    protected void changeHeight(int heightDelta, GUIBlock previousBlock) { }

    @Override
    protected void setShapes() {

        height = 30;
        int width = 40;

        blockRectangles.add(new CollisionRectangle(0, 0, width, height, Color.white));
        mainConnector = new GUIConnector("MAIN",this, 0, height / 2, Color.blue);
        subConnectors.add(new GUIConnector("SUB_1", this, width, height / 2, Color.red));
    }
}
