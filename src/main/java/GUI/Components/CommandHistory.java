package GUI.Components;
import Utility.Command;
import java.util.Stack;

public class CommandHistory {
    private Stack<Command> undoStack = new Stack<Command>();
    private Stack<Command> redoStack = new Stack<Command>();

    public void execute(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {
        System.out.println("undo");
        if (undoStack.isEmpty()) return;
        System.out.println("undo2");
        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
    }

    public void redo() {
        if (redoStack.isEmpty()) return;
        Command command = redoStack.pop();
        command.redo();
        undoStack.push(command);
    }
}
