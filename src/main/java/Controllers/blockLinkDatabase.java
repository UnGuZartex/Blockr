package Controllers;

import GUI.Blocks.GUIBlock;
import System.BlockStructure.Blocks.Block;

import java.util.*;

public class blockLinkDatabase {

    private final HashMap<GUIBlock, Block> currentBlocks = new HashMap<>();

    public Block getBlockFromGUIBlock(GUIBlock block) throws IllegalArgumentException {

        if (!currentBlocks.containsKey(block)) {
            throw new IllegalArgumentException("The given GUI block is not present in the conversion table!");
        }

        return currentBlocks.get(block);
    }

    public GUIBlock getGUIBlockFromBlock(Block block) {

        for (Map.Entry<GUIBlock, Block> entry : currentBlocks.entrySet()) {
            if (Objects.equals(block, entry.getValue())) {
                return entry.getKey();
            }
        }

        return null;
    }

    public void addBlockPair(GUIBlock GUIBlock, Block block) {
        currentBlocks.put(GUIBlock, block);
    }

    public void removeBlock(GUIBlock block) {
        currentBlocks.remove(block);
    }
}
