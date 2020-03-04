package GUI;


import GUI.Images.ImagePreLoader;

import java.awt.*;
import java.io.IOException;

public class ProgramGUI {
    public static void main(String args[]) throws IOException {
        BlockrCanvas canvas = new BlockrCanvas("Blockr", ImagePreLoader.createImageLibrary());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        canvas.setDimensions(screenSize.width, screenSize.height);

        java.awt.EventQueue.invokeLater(() -> {
          canvas.show();
        });
    }
}
