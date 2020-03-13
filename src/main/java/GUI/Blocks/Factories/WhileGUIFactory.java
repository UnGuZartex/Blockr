package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUICavityBlock;

public class WhileGUIFactory extends GUIFactory {
    private static int counter = 0;
    private String ID = "WHILE_" + counter;

    @Override
    public GUIBlock createBlock(int x, int y) {
        counter++;
        return new GUICavityBlock(ID, x,y);
    }
}
