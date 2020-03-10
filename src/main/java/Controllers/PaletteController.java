package Controllers;


import System.Logic.Palette.PaletteState;

public class PaletteController {

    private PaletteState palette = new PaletteState();;

    public void refresh() {
        palette.refillList();
    }

}
