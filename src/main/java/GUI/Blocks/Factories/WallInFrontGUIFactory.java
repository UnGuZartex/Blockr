package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUIConditionalBlock;

public class WallInFrontGUIFactory extends GUIFactory{
    private String name = "Wall In Front";

    @Override
    public GUIBlock createBlock(int x, int y) {
        return new GUIConditionalBlock(name, x,y);
    }
}
