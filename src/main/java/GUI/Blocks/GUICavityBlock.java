package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

public class GUICavityBlock extends GUIBlock {

    private int cavityHeight;

    public GUICavityBlock(int x, int y) {
        super(x, y);
    }

    @Override
    protected void setShapes() {

        int height = 80;
        int width = 150;
        int cavityWidth = 10;
        cavityHeight = 50;

        blockRectangles.add(new CollisionRectangle(0, 0, width, 80, 0, Color.white));
        blockRectangles.add(new CollisionRectangle(0, height, cavityWidth, 50, 0, Color.white));
        blockRectangles.add(new CollisionRectangle(0, height + cavityHeight, width, 40, 0, Color.white));

        mainConnector = new GUIConnector(this, width / 2, 0, Color.blue);
        subConnectors.add(new GUIConnector(this, width / 2, height, Color.red));
        subConnectors.add(new GUIConnector(this, width, height / 2, Color.red));
        subConnectors.add(new GUIConnector(this, width / 2, 120 + cavityHeight, Color.red));
    }
}
