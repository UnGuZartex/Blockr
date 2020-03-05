package GUI;


import BlockStructure.Blocks.*;
import BlockStructure.Blocks.Factory.TurnRightBlockFactory;
import BlockStructure.Functionality.ConditionalFunctionality;
import BlockStructure.Functionality.NotFunctionality;
import BlockStructure.Functionality.WallInFrontFunctionality;
import GameWorld.*;
import GameWorld.Level.*;
import ProgramArea.Program;
import java.awt.*;

public class ProgramGUI {
    public static void main(String args[]) throws Exception {
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
        level.getGrid().changeCell(0,1, CellType.WALL);

        System.out.println("INITIAL");
        System.out.println(level.getRobot().getDirection().name());
        System.out.println(level.getRobot().getX() + " - " + level.getRobot().getY());

        TurnRightBlockFactory f = new TurnRightBlockFactory();
        FunctionalBlock block = (FunctionalBlock) f.CreateBlock();
        FunctionalBlock block2 = (FunctionalBlock) f.CreateBlock();
        try {
            block.getPlug().connect(block2.getSocket());
        } catch (Exception e) {
            System.out.println("Fuck");
        }
        Program program = new Program(block, level);


        program.executeStep();
        System.out.println("EXECUTE MOVE FORWARD");
        System.out.println(level.getRobot().getDirection().name());
        System.out.println(level.getRobot().getX() + " - " + level.getRobot().getY());

        //program.executeStep();
        System.out.println("EXECUTE MOVE FORWARD");
        System.out.println(level.getRobot().getDirection().name());
        System.out.println(level.getRobot().getX() + " - " + level.getRobot().getY());

        WallInFrontFunctionality f2 = new WallInFrontFunctionality();
        NotFunctionality f3 = new NotFunctionality();
        ConditionalBlock b = new ConditionalBlock(45, f2);
        OperationalBlock not = new OperationalBlock(42, f3, 1);


        b.getPlug().connect(not.getSocketAt(0));



        f3.evaluate(not, level);
        System.out.println(f3.getEvaluation());




        java.awt.EventQueue.invokeLater(() -> {
          //canvas.show();
        });
    }
}
