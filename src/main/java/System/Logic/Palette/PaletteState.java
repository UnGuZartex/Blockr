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
        //refillList();
    }

    public Block getBlockAt(int index){
        Block toReturn = allBlocksFactory[index].CreateBlock();
//        if (GameState.currentNbBlocks < GameState.maxNbBlocks) {
//            currentBlocks[index] = allBlocksFactory[index].CreateBlock();
//        }
//        else {
//            Arrays.fill(currentBlocks, null);
//        }
        return toReturn;
    }

//    public void refillList(){
//        for(int i = 0; i < currentBlocks.length; i++) {
//            if(currentBlocks[i] == null) {
//                currentBlocks[i] = allBlocksFactory[i].CreateBlock();
//            }
//        }
//    }
}
