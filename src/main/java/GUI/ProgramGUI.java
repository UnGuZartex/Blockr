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
        WhileBlock whileBlock = new WhileBlock(58);

        WhileBlock whileBlock2 = new WhileBlock(8555);


        WallInFrontBlock wallInFrontBlock = new WallInFrontBlock(5);

        WallInFrontBlock wallInFrontBlock2 = new WallInFrontBlock(5);

        TurnRightBlock turnRightBlock = new TurnRightBlock(85);
        TurnLeftBlock turnLeftBlock = new TurnLeftBlock(88);
        TurnLeftBlock turnLeftBlock2 = new TurnLeftBlock(900);
        NotBlock notBlock = new NotBlock(900);



        MoveForwardBlock moveForwardBlock = new MoveForwardBlock(856);

        ConnectionHandler connectionHandler = new ConnectionHandler();

        connectionHandler.connect(whileBlock, whileBlock2.getCavitySubConnector());

        connectionHandler.connect(wallInFrontBlock2, whileBlock2.getConditionalSubConnector());

        connectionHandler.connect(wallInFrontBlock, whileBlock.getConditionalSubConnector());
        connectionHandler.connect(notBlock, whileBlock.getConditionalSubConnector());

        //wallInFrontBlock.getMainConnector().connect(whileBlock.getConditionalSubConnector());


        connectionHandler.connect(turnRightBlock, whileBlock.getCavitySubConnector());
        connectionHandler.connect(turnLeftBlock2, turnLeftBlock.getSubConnectors()[0]);
        //turnRightBlock.getMainConnector().connect(whileBlock.getCavitySubConnector());
        connectionHandler.connect(turnLeftBlock, whileBlock.getCavitySubConnector());

        connectionHandler.connect(moveForwardBlock, whileBlock.getSubConnectors()[0]);
        //moveForwardBlock.getMainConnector().connect(whileBlock.getSubConnectors()[0]);

        ProgramController.addBlock(whileBlock2);



        java.awt.EventQueue.invokeLater(canvas::show);
    }
}
