package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUIFunctionalityBlock;

public class TurnRightGUIFactory extends GUIFactory{
    private static int counter = 0;
    private String ID = "TURNR_" + counter;


    @Override
    public GUIBlock createBlock(int x, int y) {
        counter++;
        return new GUIFunctionalityBlock(ID, x,y);
    }
}
