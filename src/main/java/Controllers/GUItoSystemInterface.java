package Controllers;

import GUI.Blocks.Factories.*;
import GUI.Blocks.GUIBlock;
import System.BlockStructure.Blocks.Block;
import System.Logic.ProgramArea.PABlockHandler;

import java.util.HashMap;

public class GUItoSystemInterface {

    private PABlockHandler blockHandler;
    private final HashMap<String, GUIFactory> factories = new HashMap<>();
    private final HashMap<GUIBlock, Block> conversionTable = new HashMap<>();


    public GUItoSystemInterface(PABlockHandler blockHandler) {
        this.blockHandler = blockHandler;
        factories.put("IF", new IfGUIFactory());
        factories.put("WHILE", new WhileGUIFactory());
        factories.put("NOT", new NotGUIFactory());
        factories.put("WIF", new WallInFrontGUIFactory());
        factories.put("MOVEF", new MoveForwardGUIFactory());
        factories.put("TURNL", new TurnLeftGUIFactory());
        factories.put("TURNR", new TurnRightGUIFactory());
    }

    public GUIBlock createNewGUIBlock(String ID, int x, int y) {
        GUIBlock newBlock = factories.get(ID).createBlock(x, y);
        conversionTable.put(newBlock, blockHandler.getFromPalette(ID));
        return newBlock;
    }
}
