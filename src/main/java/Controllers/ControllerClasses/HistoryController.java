package Controllers.ControllerClasses;

import System.Logic.CommandHistory;
import System.Logic.ProgramArea.ProgramArea;
import Utility.Command;

/**
 * Controller used for handling the history of ui commands.
 *
 * @invar The history controller must have a valid command history at all time.
 *        | isValidCommandHistory(history)
 * @invar The history controller must have a valid program area at all time.
 *        | isValidProgramArea(programArea)
 *
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
     *         When the given history is not valid.
     * @throws IllegalArgumentException
     *         When the given program area is not valid.
     */
    public HistoryController(CommandHistory history, ProgramArea programArea) throws IllegalArgumentException {
        if (!isValidCommandHistory(history)) {
            throw new IllegalArgumentException("The given history is not valid!");
        }
        if (!isValidProgramArea(programArea)) {
            throw new IllegalArgumentException("The given program area not valid!");
        }
        this.history = history;
        this.programArea = programArea;
    }

    /**
     * Checks whether or not the given command history is valid.
     *
     * @param history The command history to check.
     *
     * @return True if and only if the given command history is effective.
     */
    public static boolean isValidCommandHistory(CommandHistory history) {
        return history != null;
    }

    /**
     * Checks whether or not the given program area is valid.
     *
     * @param programArea The program area to check.
     *
     * @return True if and only if the given program area is effective.
     */
    public static boolean isValidProgramArea(ProgramArea programArea) {
        return programArea != null;
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
     * Execute a program step command in the program area.
     *
     * @Effect The program area adds a program run command to the history,.
     */
    public void executeProgramRunCommand() {
        programArea.addProgramRunCommand();
    }

    /**
     * Execute a program reset command in the program area, if possible.
     *
     * @Effect The program area adds a program reset command to the history.
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
