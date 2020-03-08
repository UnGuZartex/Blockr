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
        IfBlock ifBlock = new IfBlock(58);

        WallInFrontBlock wallInFrontBlock = new WallInFrontBlock(5);
        TurnLeftBlock turnRightBlock = new TurnLeftBlock(85);
        MoveForwardBlock moveForwardBlock = new MoveForwardBlock(856);

        wallInFrontBlock.getLeftPlug().connect( ifBlock.getConditionalSocket());
        ifBlock.getCavityPlug().connect(turnRightBlock.getTopSocket());
        ifBlock.getBottomPlug().connect(moveForwardBlock.getTopSocket());
        turnRightBlock.getBottomPlug().connect(ifBlock.getCavitySocket());

        ProgramController.addBlock(ifBlock);



        java.awt.EventQueue.invokeLater(canvas::show);
    }
}
