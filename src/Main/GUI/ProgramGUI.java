package Main.GUI;


import Main.GameWorld.Direction;
import Main.GameWorld.Grid;

import java.awt.*;


public class ProgramGUI {
    public static void main(String args[]){
        BlockrCanvas canvas = new BlockrCanvas("Blockr");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        canvas.setDimensions(screenSize.width, screenSize.height);

        java.awt.EventQueue.invokeLater(() -> {
          canvas.show();
        });
    }
}
