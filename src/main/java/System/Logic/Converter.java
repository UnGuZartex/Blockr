package System.Logic;

import GUI.Components.GUIBlock2;
import System.BlockStructure.Blocks.Block;

import java.awt.*;
import java.util.HashMap;


public class Converter {

    private HashMap<String, Block> blockHashMap = new HashMap<>();


    public Block convert(String id) {
        return blockHashMap.get(id);
    }

    public void addBlock(String id, Block block) {
        blockHashMap.put(id, block);
    }

    public GUIBlock2 convert(Block block, int index) {
        return new GUIBlock2(0, index * 50, 50, 50, Color.BLUE);
    }

    public void deleteBlock(String id) {
        blockHashMap.remove(id);
    }
}
