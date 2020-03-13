package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUIFunctionalityBlock;

public class MoveForwardGUIFactory extends GUIFactory{
    private String name = "Move Forward";


    @Override
    public GUIBlock createBlock(int x, int y) {

        return new GUIFunctionalityBlock(name, x,y);
    }
}
