package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUIConditionalBlock;

public class WallInFrontGUIFactory extends GUIFactory{
    private static int counter = 0;
    private String ID = "WALL IN FRONT_" + counter;


    @Override
    public GUIBlock createBlock(int x, int y) {
        counter++;
        return new GUIConditionalBlock(ID, x,y);
    }
}
