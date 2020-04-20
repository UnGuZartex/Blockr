package Controllers.Controls;

import Controllers.ControllerClasses.ProgramController;
import Controllers.ProgramListener;
import GUI.Components.GUIHistory;
import GameWorldAPI.GameWorld.Result;

import java.awt.event.KeyEvent;

public class ControlHandler implements ProgramListener {

    private final ProgramController controller;

    private final GUIHistory guiHistory;

    private boolean executing;

    public ControlHandler(ProgramController controller, GUIHistory history) {
        this.controller = controller;
        this.guiHistory = history;
        controller.subscribeListener(this);
    }

    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifiers) {

        int shiftdown = KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK;
        int ctrldown = KeyEvent.CTRL_DOWN_MASK;
        if (keyCode == KeyEvent.VK_F5) {
            controller.runProgramStep();
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            controller.resetProgram();
        }

        if (keyCode == KeyEvent.VK_Z && ((modifiers & ctrldown) != 0)) {
            if ((modifiers & shiftdown) == shiftdown) {
                if (executing) {
                    controller.redoProgramStep();
                }
                else {
                    guiHistory.redo();
                }
            }
            else {
                if (executing) {
                    controller.undoProgramStep();
                } else {
                    guiHistory.undo();
                }
            }
        }

    }

    @Override
    public void onGameFinished(Result result) {
        executing = true;
    }

    @Override
    public void onProgramReset() {
        executing = false;
    }

    @Override
    public void onTooManyPrograms() {
        executing = false;
    }

    @Override
    public void onProgramInvalid() {
        executing = false;
    }

    @Override
    public void onExecuting(boolean b) {
        executing = b;
    }
}
