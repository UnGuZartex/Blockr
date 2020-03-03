package GUI;



public class ProgramGUI {
    public static void main(String args[]){


        BlockrCanvas canvas = new BlockrCanvas("Blockr");

        java.awt.EventQueue.invokeLater(() -> {
          canvas.show();
        });
    }
}
