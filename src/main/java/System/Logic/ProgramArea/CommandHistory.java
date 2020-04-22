package System.Logic.ProgramArea;

import GameWorldAPI.GameWorld.Result;

import java.util.Stack;

public class CommandHistory {
    /**
     * Variable referring to the undo stack.
     */
    private final Stack<Memento> undoStackFunc = new Stack<>();
    /**
     * Variable referring to the redo stack.
     */
    private final Stack<Memento> redoStackFunc = new Stack<>();

    private final Program originator;


    public CommandHistory(Program originator) {
        this.originator = originator;
    }

    public Result execute() {
        Memento memento = originator.createMemento();
        redoStackFunc.clear();
        undoStackFunc.push(memento);
        return memento.execute();
    }

    public void undo() {
        if (undoStackFunc.isEmpty()) return;
        Memento mem = undoStackFunc.pop();
        originator.loadMemento(mem);
        mem.undo();
        redoStackFunc.push(mem);
    }

    public void redo() {
        if (redoStackFunc.isEmpty()) return;
        Memento mem = redoStackFunc.pop();
        undoStackFunc.push(mem);
        originator.loadMemento(mem);
        mem.redo();
    }

    public void reset() {
        if (undoStackFunc.isEmpty())
            return;

        Memento mem = undoStackFunc.firstElement();
        mem.undo();
        originator.loadMemento(mem);
        redoStackFunc.clear();
        undoStackFunc.clear();
    }

    public boolean atStart() {
        return undoStackFunc.size() == 0;
    }
}
