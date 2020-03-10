package System.Logic;

import System.BlockStructure.Blocks.Block;

import java.util.HashMap;


public class Converter {

    private HashMap<String, Block> blockHashMap = new HashMap<>();


    public Block convert(String id) {
        return blockHashMap.get(id);
    }

    public void addBlock(String id, Block block) {
        blockHashMap.put(id, block);
    }

    public void deleteBlock(String id) {
        blockHashMap.remove(id);
    }
}
