package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUICavityBlock;

public class WhileGUIFactory extends GUIFactory {
    private String name = "While";

    @Override
    public GUIBlock createBlock(String id, int x, int y) {
        return new GUICavityBlock(name, id, x, y);
    }
}
