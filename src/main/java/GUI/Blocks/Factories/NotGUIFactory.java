package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUIOperatorBlock;

public class NotGUIFactory extends GUIFactory {
    private String name = "Not";


    @Override
    public GUIBlock createBlock(int x, int y) {
        return new GUIOperatorBlock(name, x,y);
    }
}
