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
        BlockrCanvas canvas = new BlockrCanvas("Blockr", library);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        canvas.setDimensions(screenSize.width, screenSize.height);
        WhileBlock whileBlock = new WhileBlock();

        WhileBlock whileBlock2 = new WhileBlock();


        WallInFrontBlock wallInFrontBlock = new WallInFrontBlock();

        WallInFrontBlock wallInFrontBlock2 = new WallInFrontBlock();

        TurnRightBlock turnRightBlock = new TurnRightBlock();
        TurnLeftBlock turnLeftBlock = new TurnLeftBlock();
        TurnLeftBlock turnLeftBlock2 = new TurnLeftBlock();
        NotBlock notBlock = new NotBlock();



        MoveForwardBlock moveForwardBlock = new MoveForwardBlock();

        ConnectionHandler connectionHandler = new ConnectionHandler();

        //connectionHandler.connect(whileBlock, whileBlock2.getCavitySubConnector());

        //connectionHandler.connect(wallInFrontBlock2, whileBlock2.getConditionalSubConnector());

        //connectionHandler.connect(wallInFrontBlock, whileBlock.getConditionalSubConnector());
        //connectionHandler.connect(notBlock, whileBlock.getConditionalSubConnector());

        //wallInFrontBlock.getMainConnector().connect(whileBlock.getConditionalSubConnector());


        connectionHandler.connect(turnRightBlock, whileBlock.getCavitySubConnector());
        connectionHandler.connect(turnLeftBlock2, turnLeftBlock.getSubConnectors()[0]);
        //turnRightBlock.getMainConnector().connect(whileBlock.getCavitySubConnector());
        connectionHandler.connect(turnLeftBlock, whileBlock.getCavitySubConnector());

        connectionHandler.connect(moveForwardBlock, whileBlock.getSubConnectors()[0]);
        //moveForwardBlock.getMainConnector().connect(whileBlock.getSubConnectors()[0]);

        ProgramController.addBlock(whileBlock);



        java.awt.EventQueue.invokeLater(canvas::show);
    }
}
