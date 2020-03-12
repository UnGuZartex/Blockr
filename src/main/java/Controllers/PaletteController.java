package Controllers;

import GUI.Components.GUIBlock2;
import System.BlockStructure.Blocks.Block;
import System.Logic.Converter;
import System.Logic.Palette.PaletteState;

public class PaletteController {
    public final PaletteState PALETTE_STATE = new PaletteState();
    public final Converter converter = new Converter();


    public GUIBlock2[] getBlocks() {
        Block[] currentBlocks = PALETTE_STATE.getCurrentBlocks();
        GUIBlock2[] GUIBlocks = new GUIBlock2[currentBlocks.length];
        for(int i = 0; i < GUIBlocks.length; i++){
            GUIBlocks[i] = converter.convert(currentBlocks[i], i);
        }

        return GUIBlocks;
    }
}
