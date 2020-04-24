package GUI.Components;

import Controllers.ControllerClasses.HistoryController;
import Controllers.ControllerClasses.ProgramController;

import java.awt.event.KeyEvent;

public class ControlHandler {

    private final ProgramController controller;
    private final HistoryController historyController;

    public ControlHandler(ProgramController controller, HistoryController historyController) {
        this.controller = controller;
        this.historyController = historyController;
    }

    public void handleKeyEvent(int keyCode, int modifiers) {

        int shiftDown = KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK;
        int ctrlDown = KeyEvent.CTRL_DOWN_MASK;

        if (keyCode == KeyEvent.VK_F5) {
            controller.runProgramStep();
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            controller.resetProgram(true);
        }

        if (keyCode == KeyEvent.VK_Z) {
            if ((modifiers ^ shiftDown) == 0) {
                historyController.redo();
            }
            else if ((modifiers ^ ctrlDown) == 0) {
                historyController.undo();
            }
        }
    }
}
