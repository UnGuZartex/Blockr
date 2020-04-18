package Controllers.Controls;

import Controllers.ControllerClasses.ProgramController;
import GUI.Components.GUIHistory;

import java.awt.event.KeyEvent;

public class ControlHandler {

    private final ProgramController controller;

    private final GUIHistory guiHistory;

    public ControlHandler(ProgramController controller, GUIHistory history) {
        this.controller = controller;
        this.guiHistory = history;
    }

    public void handleKeyEvent(int id, int keyCode, char keyChar) {

        if (keyCode == KeyEvent.VK_F5) {
            controller.runProgramStep();
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            controller.resetProgram();
        }

        if (keyCode == KeyEvent.VK_NUMPAD4) {
            if (controller.isExecuted()) {
                controller.undoProgramStep();
            }
            else{
                guiHistory.undo();
            }
        }
        if (keyCode == KeyEvent.VK_NUMPAD6) {
            if (controller.isExecuted()) {
                controller.redoProgramStep();
            }
            else {
                guiHistory.redo();
            }
        }
    }
}
