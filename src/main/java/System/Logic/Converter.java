package System.Logic;

import GUI.Components.GUIBlock2;
import System.BlockStructure.Blocks.Block;

import java.awt.*;

public class Converter {


    public GUIBlock2 convert(Block block, int index) {
        return new GUIBlock2(0,index*50, 50,50, Color.BLUE );
    }
}
