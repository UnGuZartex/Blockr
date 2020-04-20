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

    public void handleKeyEvent(int id, int keyCode, char keyChar) {

        if (keyCode == KeyEvent.VK_F5) {
            controller.runProgramStep();
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            controller.resetProgram();
        }

        if (keyCode == KeyEvent.VK_NUMPAD4) {
            if (executing) {
                controller.undoProgramStep();
            }
            else{
                guiHistory.undo();
            }
        }
        if (keyCode == KeyEvent.VK_NUMPAD6) {
            if (executing) {
                controller.redoProgramStep();
            }
            else {
                guiHistory.redo();
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
