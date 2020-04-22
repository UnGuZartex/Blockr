package GUI;


public class ProgramGUI {
    public static void main(String args[]) throws Exception {
        if (args.length != 1) {
            throw new IllegalStateException("Incorrect amount of arguments detected");
        }
        System.setProperty("GameWorldRootClass", args[0]);
        Initialiser blockrInitter = new Initialiser();
        BlockrCanvas canvas = blockrInitter.createNewCanvas();
        java.awt.EventQueue.invokeLater(canvas::show);
    }
}
