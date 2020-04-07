package GUI;


import java.awt.*;

public class ProgramGUI {
    public static void main(String args[]) throws Exception {
        //TODO CONNECTION AANLEGGEN
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        BlockrCanvas canvas = new BlockrCanvas("Blockr", screenSize.width, screenSize.height);
        java.awt.EventQueue.invokeLater(canvas::show);
    }
}
