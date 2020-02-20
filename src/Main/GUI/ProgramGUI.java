package Main.GUI;


public class ProgramGUI {
    public static void main(String args[]){
        CanvasWindow canvas = new CanvasWindow("Nice");
        canvas.height = 1070;
        canvas.width = 1910;
        java.awt.EventQueue.invokeLater(() -> {
          canvas.show();
        });
        System.out.println("Kip");
    }
}
