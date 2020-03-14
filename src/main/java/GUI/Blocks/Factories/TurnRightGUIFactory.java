package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUIFunctionalityBlock;

public class TurnRightGUIFactory extends GUIFactory{
    private String name = "Turn Right";


    @Override
    public GUIBlock createBlock(String id, int x, int y) {
        return new GUIFunctionalityBlock(name, id, x,y);
    }
}
