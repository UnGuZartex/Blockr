package Main.GUI;


import Main.GameWorld.Direction;
import Main.GameWorld.Grid;

import java.awt.*;


public class ProgramGUI {
    public static void main(String args[]) {
        ImagePreLoader.loadImages();
        BlockrCanvas canvas = new BlockrCanvas("Blockr");
        canvas.setDimensions(1920, 1080);

        java.awt.EventQueue.invokeLater(() -> {
          canvas.show();
        });
    }
}
