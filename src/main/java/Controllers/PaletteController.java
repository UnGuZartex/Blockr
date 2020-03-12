package Controllers;

<<<<<<< HEAD
import GUI.Components.GUIBlock2;
import System.BlockStructure.Blocks.Block;
import System.Logic.Converter;
import System.Logic.Palette.PaletteState;
=======
>>>>>>> 73dc16e351fe71aa1df6685dd8aa41d162396d0c

public class PaletteController {


<<<<<<< HEAD
    public GUIBlock2[] getBlocks() {
        Block[] currentBlocks = PALETTE_STATE.getCurrentBlocks();
        GUIBlock2[] GUIBlocks = new GUIBlock2[currentBlocks.length];
        for(int i = 0; i < GUIBlocks.length; i++){
            GUIBlocks[i] = converter.convert(currentBlocks[i], i);
        }

        return GUIBlocks;
    }
=======
>>>>>>> 73dc16e351fe71aa1df6685dd8aa41d162396d0c
}
