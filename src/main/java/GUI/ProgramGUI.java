package GUI;


import BlockStructure.Blocks.*;
import BlockStructure.Functionality.Functionality;
import BlockStructure.Functionality.MoveForwardFunctionality;
import GameWorld.*;
import GameWorld.Level.*;
import ProgramArea.Program;

import java.awt.*;

public class ProgramGUI {
    public static void main(String args[]) {
        ImagePreLoader.loadImages();
        BlockrCanvas canvas = new BlockrCanvas("Blockr");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        canvas.setDimensions(screenSize.width, screenSize.height);

        Cell[][] cells = new Cell[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                cells[i][j] = new Cell(CellType.BLANK);
            }
        }
        Level level = new Level(new Point(0,0), Direction.RIGHT, cells);

        System.out.println("INITIAL");
        System.out.println(level.getRobot().getDirection().name());
        System.out.println(level.getRobot().getX() + " - " + level.getRobot().getY());

        Functionality functionality = new MoveForwardFunctionality();
        FunctionalBlock block = new FunctionalBlock(0, functionality);
        Program program = new Program(block, level);
        program.executeStep();

        System.out.println("EXECUTE MOVE FORWARD");
        System.out.println(level.getRobot().getDirection().name());
        System.out.println(level.getRobot().getX() + " - " + level.getRobot().getY());




        java.awt.EventQueue.invokeLater(() -> {
          canvas.show();
        });
    }
}
