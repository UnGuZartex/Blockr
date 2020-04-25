package GUI.Components;

import Controllers.ControllerClasses.HistoryController;

import java.awt.event.KeyEvent;

/**
 * A class used for handling key events.
 *
 * @invar The control handler must contain a valid history controller at all time.
 *        | historyController != null
 *
 * @author Alpha-team
 */
public class ControlHandler {

    /**
     * Variable referring to the history controller of this control handler.
     */
    private final HistoryController historyController;

    /**
     * Create a new control handler with the given history controller.
     *
     * @param historyController The given history controller.
     *
     * @post The current history controller is set to the given controller.
     */
    public ControlHandler(HistoryController historyController) {
        this.historyController = historyController;
    }

    /**
     * Handle a key event with a given key code and modifiers.
     *
     * @param keyCode The given key code indicating the currently pressed key.
     * @param modifiers The given modifiers indicating if ctrl or shift is pressed.
     *
     * @effect If f5 is pressed, the history controller will execute a program run command.
     * @effect If escape is pressed, the history controller will execute program reset command.
     * @effect If ctrl + Z is pressed, the history controller will undo the history.
     * @effect If ctrl + shift + Z is pressed, the history controller will redo the history.
     */
    public void handleKeyEvent(int keyCode, int modifiers) {

        int shiftDown = KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK;
        int ctrlDown = KeyEvent.CTRL_DOWN_MASK;

        if (keyCode == KeyEvent.VK_F5) {
            historyController.executeProgramRunCommand();
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            historyController.executeProgramResetCommand();
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
