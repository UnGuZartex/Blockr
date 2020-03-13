package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUIFunctionalityBlock;

public class MoveForwardGUIFactory extends GUIFactory{
    private static int counter = 0;
    private String ID = "MOVE FORWARD_" + counter;


    @Override
    public GUIBlock createBlock(int x, int y) {
        counter++;
        return new GUIFunctionalityBlock(ID, x,y);
    }
}
