package GUI;


import java.awt.*;

public class ProgramGUI {
    public static void main(String args[]) {
        ImagePreLoader.loadImages();
        BlockrCanvas canvas = new BlockrCanvas("Blockr");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        canvas.setDimensions(screenSize.width, screenSize.height);

        java.awt.EventQueue.invokeLater(() -> {
          canvas.show();
        });
    }
}
