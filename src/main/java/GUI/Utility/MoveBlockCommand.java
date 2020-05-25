package GUI.Utility;

import GUI.Handlers.GUIBlockHandler;
import GameWorldAPI.Utility.Snapshot;
import Utility.Command;
import Utility.Position;

import java.awt.event.MouseEvent;

/**
 * A class representing a move block command.
 *
 * @invar The start position may not be null.
 *        | start != null
 * @invar The end position may not be null.
 *        | end != null
 * @invar The gui block handler may not be null.
 *        | guiBlockHandler != null
 *
 * @author Alpha-team
 */
public class MoveBlockCommand implements Command
{
    /**
     * Variable referring to the start and end position of the mouse given to this command.
     */
    private final Position start, end;

    /**
     * Variable referring to the linked gui block handler.
     */
    private final GUIBlockHandler guiBlockHandler;

    /**
     * Variable referring to the stored gui block snapshot.
     */
    private Snapshot GUIBlocksSnapshot;

    /**
     * Create a new move block command with a given start position, end position
     * and gui block handler.
     *
     * @param start The given start position.
     * @param end The given end position.
     * @param guiBlockHandler The given gui block handler.
     *
     * @post The current start position is set to the given start position.
     * @post The current end position is set to the given end position.
     * @post The current gui block handler is set to the given gui block handler.
     *
     * @throws IllegalArgumentException
     *         When the given start position is not valid.
     * @throws IllegalArgumentException
     *         When the given end position is not valid.
     * @throws IllegalArgumentException
     *         When the given gui block handler is not valid.
     */
    public MoveBlockCommand(Position start, Position end, GUIBlockHandler guiBlockHandler) throws IllegalArgumentException {
        if (!isValidStartPosition(start)) {
            throw new IllegalArgumentException("Start is not valid!");
        }
        if (!isValidEndPosition(end)) {
            throw new IllegalArgumentException("End is not valid!");
        }
        if (!isValidBlockHandler(guiBlockHandler)) {
            throw new IllegalArgumentException("The gui block handler is not valid!");
        }
        this.start = start;
        this.end = end;
        this.guiBlockHandler = guiBlockHandler;
    }

    /**
     * Check whether or not the given position is a valid start position.
     *
     * @param start The position to check.
     *
     * @return True if and only if the given position is not null.
     */
    public static boolean isValidStartPosition(Position start) {
        return start != null;
    }

    /**
     * Check whether or not the given position is a valid end position.
     *
     * @param end The position to check.
     *
     * @return True if and only if the given position is not null.
     */
    public static boolean isValidEndPosition(Position end) {
        return end != null;
    }

    /**
     * Check whether or not the given block handler is a valid.
     *
     * @param blockHandler The block handler to check.
     *
     * @return True if and only if the given block handler is not null.
     */
    public static boolean isValidBlockHandler(GUIBlockHandler blockHandler) {
        return blockHandler != null;
    }

    /**
     * execute this move block command.
     *
     * @post The current gui block handler snapshot is set to a newly created snapshot.
     *
     * @effect Pressing the mouse is simulated at the start position.
     * @effect Dragging the mouse is simulated at the end position.
     * @effect Releasing the mouse is simulated at the end position.
     */
    @Override
    public void execute() {
        GUIBlocksSnapshot = guiBlockHandler.createSnapshot();
        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, start.getX(), start.getY());
        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, end.getX(), end.getY());
        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, end.getX(), end.getY());
    }

    /**
     * Undo this move block command.
     *
     * @Effect The currently stored gui block handler snapshot is loaded
     *         into the current gui block handler.
     */
    @Override
    public void undo() {
        guiBlockHandler.loadSnapshot(GUIBlocksSnapshot);
    }
}
