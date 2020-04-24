package System.Logic;
import Utility.Command;
import java.util.Stack;

/**
 * Class used for handling project history.
 * This class holds two stacks of commands for dynamic history management.
 */
public class CommandHistory {
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    /**
     * Execute a given command and add it to history.
     *
     * @param command The command to be executed.
     *
     * @effect The command is executed.
     * @effect The command is pushed on the undo stack.
     * @effect The redo stack is cleared.
     */
    public void execute(Command command) {
        command.execute();
        System.out.println("added command");
        undoStack.push(command);
        redoStack.clear();
    }

    /**
     * Undo the most recent command in this history.
     *
     * @effect The most recent command on the undo stack
     *         is undone, if the stack isn't empty.
     * @effect The most recent command on the undo stack
     *         is removed from the undo stack, if the
     *         stack isn't empty.
     * @effect The most recent command on the undo stack
     *         is added to the redo stack, if the undo
     *         stack isn't empty.
     */
    public void undo() {
        if (undoStack.isEmpty()) return;
        System.out.println("undo command");
        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
    }

    /**
     * Redo the most recent undone command in this history.
     *
     * @effect The most recent undone command on the redo stack
     *         is redone, if the stack isn't empty.
     * @effect The most recent undone command on the redo stack
     *         is removed from the redo stack, if the
     *         stack isn't empty.
     * @effect The most recent undone command on the redo stack
     *         is added to the undo stack, if the redo
     *         stack isn't empty.
     */
    public void redo() {
        if (redoStack.isEmpty()) return;
        System.out.println("redo command");
        Command command = redoStack.pop();
        command.redo();
        undoStack.push(command);
    }
}
