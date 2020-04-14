package GameWorld.History;

import GameWorldAPI.History.History;
import GameWorldAPI.History.Snapshot;
import java.util.Stack;

/**
 * TODO
 */
public class GenericHistory implements History {

    /**
     * Variable referring to the originator this history works on.
     */
    private final HistoryTracked originator;
    /**
     * Variable referring to the undo stack.
     */
    private final Stack<Snapshot> undoStack = new Stack<>();
    /**
     * Variable referring to the redo stack.
     */
    private final Stack<Snapshot> redoStack = new Stack<>();

    /**
     * Initialise a new history with given originator.
     *
     * @param originator The originator for this history.
     *
     * @post The originator of this history is set to the given originator.
     */
    public GenericHistory(HistoryTracked originator) {
        this.originator = originator;
    }

    /**
     * Add the given snapshot to the history.
     *
     * @param snapshot The snapshot to add to the history.
     *
     * @effect The given snapshot is added to the undo stack of this history.
     * @effect The redo stack is emptied.
     */
    @Override
    public void add(Snapshot snapshot) {
        undoStack.push(snapshot);
        redoStack.clear();
    }

    /**
     * Undo the latest snapshot added to the history.
     *
     * @effect The current state of the originator is saved in a snapshot and
     *         added to the redo stack, if any undo can be executed.
     * @effect The snapshot on top of the undo stack is restored for the originator,
     *         if any undo snapshot exists.
     * @effect The snapshot which is being undone, if any exists, is printed.
     */
    @Override
    public void undo() {
        if (!undoStack.isEmpty()) {
            Snapshot currentState = originator.createSnapshot();
            redoStack.push(currentState);
            Snapshot snapshot = undoStack.pop();
            System.out.println("Undoing: " + snapshot);
            originator.loadSnapshot(snapshot);
        }
    }

    /**
     * Redo the latest action which was undone.
     *
     * @effect A snapshot of the current state of the originator is saved and added
     *         to the undo stack, if any redo is executed.
     * @effect The snapshot on top of the redo stack is loaded for the originator,
     *         if any exists.
     * @effect The snapshot which is being redone, if any exists, is printed.
     */
    @Override
    public void redo() {
        if (!redoStack.isEmpty()) {
            Snapshot currentState = originator.createSnapshot();
            undoStack.push(currentState);
            Snapshot snapshot = redoStack.pop();
            System.out.println("Redoing: " + snapshot);
            originator.loadSnapshot(snapshot);
        }
    }

    /**
     * Reset this history.
     *
     * @effect The bottom element of the undo stack is loaded in the originator,
     *         if any exists.
     * @effect The snapshot which is being reset to, if any exists, is printed.
     * @effect The undo stack is cleared, if it wasn't empty already.
     * @effect The redo stack is cleared.
     */
    @Override
    public void reset() {
        if (!undoStack.isEmpty()) {
            Snapshot firstElement = undoStack.firstElement();
            System.out.println("Resetting to: " + firstElement);
            originator.loadSnapshot(firstElement);
            undoStack.clear();
        }
        redoStack.clear();
    }
}