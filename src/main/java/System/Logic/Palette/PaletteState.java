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
        allBlocksFactory.put("WALL IN FRONT", new WallInFrontBlockFactory());
        allBlocksFactory.put("MOVE FORWARD", new MoveForwardBlockFactory());
        allBlocksFactory.put("TURN LEFT", new TurnLeftBlockFactory());
        allBlocksFactory.put("TURN RIGHT", new TurnRightBlockFactory());
    }

    public Block getBlockAt(String ID){
        if (allBlocksFactory.containsKey(ID)) {
            Block toReturn = allBlocksFactory.get(ID).CreateBlock();
            return toReturn;
        }
        else {
            throw new IllegalArgumentException("Invalid ID, choose between: " + allBlocksFactory.keySet());
        }
    }
}
