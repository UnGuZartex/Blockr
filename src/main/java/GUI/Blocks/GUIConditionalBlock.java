package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

public class GUIConditionalBlock extends GUIBlock {

    public GUIConditionalBlock(String name, String id, int x, int y) {
        super(name, id, x, y);
    }

    @Override
    protected void changeHeight(int heightDelta, GUIBlock previousBlock) { }

    @Override
    protected void setShapes() {

        height = 30;
        int width = 100;

        blockRectangles.add(new CollisionRectangle(0, 0, width, height, Color.white));
        mainConnector = new GUIConnector("MAIN", this, 0, height / 2, Color.blue);
    }
}
