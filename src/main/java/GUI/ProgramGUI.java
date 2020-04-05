package GUI;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.GameWorldType;
import System.GameWorld.Level.LevelLoader;

import java.awt.*;
import java.net.URL;
import java.net.URLClassLoader;

public class ProgramGUI {
    public static void main(String args[]) throws Exception {
        URL[] urls = { new URL("jar:file:" + System.getenv("GameWorldFileLocation")+"!/") };
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        Class c = Class.forName(System.getenv("GameWorldRootClass"), true, cl);

        GameWorldType initter = (GameWorldType) c.getConstructor().newInstance();
        GameWorld gameWorld = initter.createNewGameworld();
        //TODO CONNECTION AANLEGGEN
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        BlockrCanvas canvas = new BlockrCanvas("Blockr", screenSize.width, screenSize.height);
        java.awt.EventQueue.invokeLater(canvas::show);
    }
}
