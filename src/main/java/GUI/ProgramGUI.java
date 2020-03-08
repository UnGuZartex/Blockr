package GUI;



import Controllers.PaletteController;
import Controllers.ProgramController;
import System.BlockStructure.Blocks.*;
import System.GameWorld.Level.LevelLoader;
import System.Logic.ProgramArea.Program;
import System.Logic.ProgramArea.ProgramArea;

import java.awt.*;

public class ProgramGUI {
    public static void main(String args[]) throws Exception {
        ImagePreLoader.loadImages();
        LevelLoader loader = new LevelLoader();
        loader.loadLevel();
        BlockrCanvas canvas = new BlockrCanvas("Blockr");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        canvas.setDimensions(screenSize.width, screenSize.height);
        WhileBlock whileBlock = new WhileBlock(58);

        WallInFrontBlock wallInFrontBlock = new WallInFrontBlock(5);
        TurnRightBlock turnRightBlock = new TurnRightBlock(85);
        MoveForwardBlock moveForwardBlock = new MoveForwardBlock(856);

        wallInFrontBlock.getLeftPlug().connect( whileBlock.getConditionalSocket());
        whileBlock.getCavityPlug().connect(turnRightBlock.getTopSocket());
        whileBlock.getBottomPlug().connect(moveForwardBlock.getTopSocket());
        turnRightBlock.getBottomPlug().connect(whileBlock.getCavitySocket());

        ProgramController.addBlock(whileBlock);



        java.awt.EventQueue.invokeLater(canvas::show);
    }
}
