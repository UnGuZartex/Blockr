package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

public class GUIOperatorBlock extends GUIBlock {

    private GUIConnector subConnector;

    public GUIOperatorBlock(String name, String id, int x, int y) {
        super(name, id, x, y);
}

    @Override
    public void setColor(Color color) {
        super.setColor(color);

        if (subConnector.isConnected()) {
            subConnector.getConnectedGUIBlock().setColor(color);
        }
    }

    @Override
    protected void changeHeight(int heightDelta, GUIBlock previousBlock) { }

    @Override
    protected void setShapes() {
        height = 30;
        width = 40;

        blockRectangles.add(new CollisionRectangle(0, 0, width, height, Color.white));
        mainConnector = new GUIConnector("MAIN",this, 0, height / 2, Color.blue);
        subConnector = new GUIConnector("SUB_1", this, width, height / 2, Color.red);
        subConnectors.add(subConnector);
    }
}
