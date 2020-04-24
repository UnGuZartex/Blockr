package Controllers.ControllerClasses;

import System.Logic.CommandHistory;
import Utility.Command;

/**
 * Controller used for handling the history of ui commands.
 */
public class HistoryController {
    private CommandHistory history;

    /**
     * Create a new history controller with a given command history.
     *
     * @param history The given command history.
     *
     * @post The current command history is set to the given history.
     */
    public HistoryController(CommandHistory history) {
        this.history = history;
    }

    /**
     * Execute a command and add it to the history.
     *
     * @param command The given command to be executed.
     *
     * @effect The history executed and adds the command.
     */
    public void execute(Command command) {
        history.execute(command);
    }

    /**
     * Undo the history one step.
     *
     * @effect The history is undone one step.
     */
    public void undo() {
        history.undo();
    }

    /**
     * Redo the history one step.
     *
     * @effect The history is redone one step.
     */
    public void redo() {
        history.redo();
    }
}
