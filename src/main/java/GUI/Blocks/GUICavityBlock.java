package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

public class GUICavityBlock extends GUIBlock {

    private int cavityHeight;

    public GUICavityBlock(String name, int x, int y) {
        super(name,x, y);
    }

    @Override
    protected void setShapes() {
        int height = 30;
        int lowerheight = 10;

        int width = 100;
        int cavityWidth = 10;
        cavityHeight = 20;

        blockRectangles.add(new CollisionRectangle(0, 0, width, height, 0, Color.white));
        blockRectangles.add(new CollisionRectangle(0, height, cavityWidth, cavityHeight, 0, Color.white));
        blockRectangles.add(new CollisionRectangle(0, height + cavityHeight, width, lowerheight, 0, Color.white));

        mainConnector = new GUIConnector("MAIN", this, width / 2, 0, Color.blue);
        subConnectors.add(new GUIConnector("CAVITY", this, (width+cavityWidth) / 2, height, Color.red));
        subConnectors.add(new GUIConnector("CONDITIONAL", this, width, height / 2, Color.red));
        subConnectors.add(new GUIConnector("SUB_1",this, width / 2, height + lowerheight + cavityHeight, Color.red));
    }
}
