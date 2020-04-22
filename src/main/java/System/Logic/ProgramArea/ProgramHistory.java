package System.Logic.ProgramArea;

import GameWorldAPI.GameWorld.Result;

import java.util.Stack;

public class ProgramHistory {
    /**
     * Variable referring to the undo stack.
     */
    private final Stack<Memento> undoStackFunc = new Stack<>();
    /**
     * Variable referring to the redo stack.
     */
    private final Stack<Memento> redoStackFunc = new Stack<>();



    public Result execute(Memento memento) {
        redoStackFunc.clear();
        undoStackFunc.push(memento);
        return memento.execute();
    }

    public void undo() {
        if (undoStackFunc.isEmpty()) return;
        Memento mem = undoStackFunc.pop();
        mem.undo();
        redoStackFunc.push(mem);
    }

    public void redo() {
        if (redoStackFunc.isEmpty()) return;
        Memento mem = redoStackFunc.pop();
        undoStackFunc.push(mem);
        mem.redo();
    }

    public void reset() {
        if (undoStackFunc.isEmpty())
            return;

        Memento mem = undoStackFunc.firstElement();
        mem.undo();
        redoStackFunc.clear();
        undoStackFunc.clear();
    }

    public boolean atStart() {
        return undoStackFunc.size() == 0;
    }
}
