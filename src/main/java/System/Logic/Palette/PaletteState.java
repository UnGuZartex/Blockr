package System.Logic.Palette;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.Factory.*;
import System.GameState.GameState;

import java.util.Arrays;
import java.util.HashMap;

public class PaletteState {
    private final HashMap<String, BlockFactory> allBlocksFactory = new HashMap<>();



    public PaletteState() {
        allBlocksFactory.put("IF", new IfBlockFactory());
        allBlocksFactory.put("WHILE", new WhileBlockFactory());
        allBlocksFactory.put("NOT", new NotBlockFactory());
        allBlocksFactory.put("WIF", new WallInFrontBlockFactory());
        allBlocksFactory.put("MOVEF", new MoveForwardBlockFactory());
        allBlocksFactory.put("TURNL", new TurnLeftBlockFactory());
        allBlocksFactory.put("TURNR", new TurnRightBlockFactory());

    }

    public Block getBlockAt(String ID){
        Block toReturn = allBlocksFactory.get(ID).CreateBlock();
        return toReturn;
    }
}
