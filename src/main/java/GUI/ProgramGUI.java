package GUI;


import Controllers.ProgramController;
import GUI.Images.ImageLibrary;
import GUI.Images.ImagePreLoader;
import System.BlockStructure.Blocks.*;
import System.GameWorld.Level.LevelLoader;
import System.Logic.ProgramArea.ConnectionHandler;

import java.awt.*;

public class ProgramGUI {
    public static void main(String args[]) throws Exception {
        ImageLibrary library = ImagePreLoader.createImageLibrary();
        LevelLoader loader = new LevelLoader();
        loader.loadLevel();
        BlockrCanvas canvas = new BlockrCanvas("Blockr", library, 900, 600);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //canvas.setDimensions(screenSize.width, screenSize.height);
        java.awt.EventQueue.invokeLater(canvas::show);
    }
}
