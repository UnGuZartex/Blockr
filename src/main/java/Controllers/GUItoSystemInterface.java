package Controllers;

import GUI.Blocks.Factories.*;
import GUI.Blocks.GUIBlock;
import GUI.Components.GUIConnector;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.SubConnector;
import System.Logic.ProgramArea.PABlockHandler;

import java.util.*;

public class GUItoSystemInterface {

    private PABlockHandler blockHandler;
    private final HashMap<String, GUIFactory> factories = new HashMap<>();
    private final HashMap<GUIBlock, Block> conversionTable = new HashMap<>();

    public GUItoSystemInterface(PABlockHandler blockHandler) {
        this.blockHandler = blockHandler;
        factories.put("IF", new IfGUIFactory());
        factories.put("WHILE", new WhileGUIFactory());
        factories.put("NOT", new NotGUIFactory());
        factories.put("WALL IN FRONT", new WallInFrontGUIFactory());
        factories.put("MOVE FORWARD", new MoveForwardGUIFactory());
        factories.put("TURN LEFT", new TurnLeftGUIFactory());
        factories.put("TURN RIGHT", new TurnRightGUIFactory());
    }

    public GUIBlock createNewGUIBlock(String id, int x, int y) {
        if (factories.containsKey(id)) {
            GUIBlock newBlock = factories.get(id).createBlock(id, x, y);
            if (newBlock != null) {
                conversionTable.put(newBlock, blockHandler.getFromPalette(id));
            }
            return newBlock;
        }
        else {
            throw new IllegalArgumentException("Invalid ID, choose between: " + factories.keySet());
        }
    }

    public Block getBlockFromGUIBlock(GUIBlock block) throws IllegalArgumentException {

        if (conversionTable.containsKey(block)) {
            throw new IllegalArgumentException("The given GUI block is not present in the conversion table!");
        }

        return conversionTable.get(block);
    }

    public GUIBlock getGUIBlockFromBlock(Block block) {

        for (Map.Entry<GUIBlock, Block> entry : conversionTable.entrySet()) {
            if (Objects.equals(block, entry.getValue())) {
                return entry.getKey();
            }
        }

        return null;
    }

    public SubConnector getSubConnectorFromGUIBlockWithID(GUIBlock block, String ID) {
        Block searchedBlock = getBlockFromGUIBlock(block);
        return searchedBlock.getSubConnectorWithID(ID);
    }

    public void removeBlock(GUIBlock block) {
        conversionTable.remove(block);
    }
}
