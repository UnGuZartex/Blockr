package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUICavityBlock;

public class IfGUIFactory extends GUIFactory {
    private String name = "If";


    @Override
    public GUIBlock createBlock(int x, int y) {
        return new GUICavityBlock(name, x,y);
    }
}
