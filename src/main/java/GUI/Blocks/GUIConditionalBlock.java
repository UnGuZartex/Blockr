package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

public class GUIConditionalBlock extends GUIBlock {

    public GUIConditionalBlock(String ID, int x, int y) {
        super(ID, x, y);
    }

    @Override
    protected void setShapes() {
        int height = 80;
        int width = 150;

        blockRectangles.add(new CollisionRectangle(0, 0, width, height, 0, Color.white));
        mainConnector = new GUIConnector(this, 0, height / 2, Color.blue);
    }

}
