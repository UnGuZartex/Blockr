package System.Logic.Palette;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.Factory.*;
import System.GameState.GameState;

import java.util.Arrays;

public class PaletteState {
    private final BlockFactory[] allBlocksFactory = new BlockFactory[]
            {
                    new IfBlockFactory(),
                    new WhileBlockFactory(),
                    new NotBlockFactory(),
                    new WallInFrontBlockFactory(),
                    new MoveForwardBlockFactory(),
                    new TurnLeftBlockFactory(),
                    new TurnRightBlockFactory()
            };


    public PaletteState() {
    }

    public Block getBlockAt(int index){
        Block toReturn = allBlocksFactory[index].CreateBlock();
        return toReturn;
    }
}
