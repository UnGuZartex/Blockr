package Controllers.Controls;

import Controllers.ControllerClasses.ProgramController;

import java.awt.event.KeyEvent;

public class ControlHandler {

    private final ProgramController controller;

    public ControlHandler(ProgramController controller) {
        this.controller = controller;
    }

    public void handleKeyEvent(int id, int keyCode, char keyChar) {
        System.out.println(id);
        if (keyCode == KeyEvent.VK_F5) {
            controller.runProgramStep();
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            controller.resetProgram();
        }
        if (keyCode == KeyEvent.VK_NUMPAD4) {
            controller.undoProgramStep();
        }
        if (keyCode == KeyEvent.VK_NUMPAD6) {
            controller.redoProgramStep();
        }
    }
}
