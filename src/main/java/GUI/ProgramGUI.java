package GUI;

/**
 * Starter class for this project.
 */
public class ProgramGUI {

    /**
     * Start the project with given project arguments.
     *
     * @param args The given project arguments.
     *
     * @throws Exception
     *         When an incorrect amount of arguments is given to this method.
     *
     * @effect The GameWorldRootClass system property is set.
     * @effect The project initialiser is created.
     * @effect The Blockr canvas is created.
     */
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
