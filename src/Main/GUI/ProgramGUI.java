package Main.GUI;


import Main.GameWorld.Direction;
import Main.GameWorld.Grid;

import java.awt.*;


public class ProgramGUI {
    public static void main(String args[]){
        BlockrCanvas canvas = new BlockrCanvas("Nice");

        int[][] list = new int[2][5];


        System.out.println(list.length);
        System.out.println(list[0].length);

        canvas.setDimensions(800, 500);

        java.awt.EventQueue.invokeLater(() -> {
          //canvas.show();
        });
    }
}
