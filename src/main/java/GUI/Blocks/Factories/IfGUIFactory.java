package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUICavityBlock;

public class IfGUIFactory extends GUIFactory {
    private String name = "If";

    @Override
    public GUIBlock createBlock(String id, int x, int y) {
        return new GUICavityBlock(name, id, x,y);
    }
}
