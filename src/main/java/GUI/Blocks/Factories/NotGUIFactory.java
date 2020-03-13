package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUIOperatorBlock;

public class NotGUIFactory extends GUIFactory {
    private static int counter = 0;
    private String ID = "NOT_" + counter;


    @Override
    public GUIBlock createBlock(int x, int y) {
        counter++;
        return new GUIOperatorBlock(ID, x,y);
    }
}
