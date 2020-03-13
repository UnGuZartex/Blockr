package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

public class GUIOperatorBlock extends GUIBlock {
    public GUIOperatorBlock(String ID, int x, int y) {
        super(ID);

        int height = 80;
        int width = 150;

        blockRectangles.add(new CollisionRectangle(0, 0, width, height, 0, Color.white));
        mainConnector = new GUIConnector(this, 0, height / 2, Color.blue);
        subConnectors.add(new GUIConnector(this, width, height / 2, Color.red));

        setPosition(x, y);
    }
}
