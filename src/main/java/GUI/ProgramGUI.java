package GUI;


public class ProgramGUI {
    public static void main(String args[]) throws Exception {
        Initialiser blockrInitter = new Initialiser();
        BlockrCanvas canvas = blockrInitter.createNewCanvas();
        java.awt.EventQueue.invokeLater(canvas::show);
    }
}
