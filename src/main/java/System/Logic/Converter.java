package System.Logic;

import GUI.GUIBlock;
import System.BlockStructure.Blocks.Block;

import java.awt.*;

public class Converter {


    public GUIBlock convert(Block block, int index) {
        return new GUIBlock(0,index*50, 50,50, Color.BLUE );
    }
}
