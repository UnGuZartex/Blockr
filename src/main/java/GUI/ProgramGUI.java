package GUI;


import Controllers.ConnectionController;
import Controllers.GUItoSystemInterface;
import Controllers.ProgramController;
import System.GameWorld.Level.LevelLoader;
import System.Logic.ProgramArea.PABlockHandler;

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
