package Controllers;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUICavityBlock;
import GUI.Blocks.GUIConditionalBlock;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.CavityBlock;
import System.BlockStructure.Blocks.ConditionalBlock;
import System.BlockStructure.Blocks.IfBlock;
import System.Logic.ProgramArea.PABlockHandler;

import java.util.*;

public class GUItoSystemInterface {

    private PABlockHandler blockHandler;
    private final HashMap<GUIBlock, Block> currentBlocks = new HashMap<>();

    private final HashMap<GUIBlock, Block> conversionTable() {
        put(new GUICavityBlock(), new IfBlock()),
        put(new GUIConditionalBlock(), new ConditionalBlock());
    };

    public GUItoSystemInterface(PABlockHandler blockHandler) {
        this.blockHandler = blockHandler;
    }

    public GUIBlock createNewGUIBlock(String id, int x, int y) {
        if (factories.containsKey(id)) {
            GUIBlock newBlock = factories.get(id).createBlock(id, x, y);
            if (newBlock != null) {
                currentBlocks.put(newBlock, blockHandler.getFromPalette(id));
            }
            return newBlock;
        }
        else {
            throw new IllegalArgumentException("Invalid ID, choose between: " + factories.keySet());
        }
    }

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

    public void removeBlock(GUIBlock block) {
        currentBlocks.remove(block);
    }
}
