package Controllers.ControllerClasses;

import GUI.Components.CommandHistory;
import Utility.Command;

public class HistoryController {
    private CommandHistory history;

    public HistoryController(CommandHistory history) {
        this.history = history;
    }

    public void execute(Command command) {
        history.execute(command);
    }

    public void undo() {
        history.undo();
    }

    public void redo() {
        history.redo();
    }
}
