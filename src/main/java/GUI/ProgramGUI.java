package GUI;


import Controllers.ProgramController;
import GUI.Images.ImageLibrary;
import GUI.Images.ImagePreLoader;
import System.BlockStructure.Blocks.MoveForwardBlock;
import System.BlockStructure.Blocks.TurnRightBlock;
import System.BlockStructure.Blocks.WallInFrontBlock;
import System.BlockStructure.Blocks.WhileBlock;
import System.GameWorld.Level.LevelLoader;

import java.awt.*;

public class ProgramGUI {
    public static void main(String args[]) throws Exception {
        ImageLibrary library = ImagePreLoader.createImageLibrary();
        LevelLoader loader = new LevelLoader();
        loader.loadLevel();
        BlockrCanvas canvas = new BlockrCanvas("Blockr", library);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        canvas.setDimensions(screenSize.width, screenSize.height);
        WhileBlock whileBlock = new WhileBlock(58);

        WallInFrontBlock wallInFrontBlock = new WallInFrontBlock(5);
        TurnRightBlock turnRightBlock = new TurnRightBlock(85);
        MoveForwardBlock moveForwardBlock = new MoveForwardBlock(856);

        wallInFrontBlock.getMainConnector().connect(whileBlock.getConditionalSubConnector());

        turnRightBlock.getMainConnector().connect(whileBlock.getCavitySubConnector());
        moveForwardBlock.getMainConnector().connect(whileBlock.getSubConnectors()[0]);

        ProgramController.addBlock(whileBlock);



        java.awt.EventQueue.invokeLater(canvas::show);
    }
}
