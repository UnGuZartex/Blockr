package Controllers.ControllerClasses;

import System.Logic.CommandHistory;
import System.Logic.ProgramArea.ProgramArea;
import Utility.Command;

/**
 * Controller used for handling the history of ui commands.
 *
 * @invar The history controller must have an effective command history at all time.
 *        | history != null
 * @invar The history controller must have an effective program area at all time.
 *        | programArea != null
 * @author Alhpa-team
 */
public class HistoryController {

    /**
     * Variable referring to the history of this history controller.
     */
    private final CommandHistory history;
    /**
     * Variable referring to the program area of this history controller.
     */
    private final ProgramArea programArea;

    /**
     * Create a new history controller with a given command history.
     *
     * @param history The command history for this controller.
     * @param programArea The program area for this controller.
     *
     * @post The command history is set to the given history.
     * @post The program area is set to the given program area.
     *
     * @throws IllegalArgumentException
     *         When the given history is not effective.
     * @throws IllegalArgumentException
     *         When the given program area is not effective.
     */
    public HistoryController(CommandHistory history, ProgramArea programArea) throws IllegalArgumentException {
        if (history == null) {
            throw new IllegalArgumentException("The given history is null!");
        }
        if (programArea == null) {
            throw new IllegalArgumentException("The given program area is null!");
        }
        this.history = history;
        this.programArea = programArea;
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
     * Execute a program step command in the program area,
     * if possible.
     *
     * @Effect The program area adds a program run command to the history,
     *         if possible.
     */
    public void executeProgramRunCommand() {
        programArea.addProgramRunCommand();
    }

    /**
     * Execute a program reset command in the program area,
     * if possible.
     *
     * @Effect The program area  adds a program reset command to the history,
     *         if possible.
     */
    public void executeProgramResetCommand() {
        programArea.addProgramResetCommand();
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
