package GUI;


import Controllers.ProgramController;
import GUI.Images.ImageLibrary;
import GUI.Images.ImagePreLoader;
import System.GameWorld.Level.LevelLoader;

import java.awt.*;

public class ProgramGUI {
    public static void main(String args[]) throws Exception {
        LevelLoader loader = new LevelLoader();
        loader.loadLevel();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        BlockrCanvas canvas = new BlockrCanvas("Blockr", screenSize.width, screenSize.height);
        java.awt.EventQueue.invokeLater(canvas::show);
    }
}
