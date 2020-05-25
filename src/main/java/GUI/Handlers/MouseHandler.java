package GUI.Handlers;

import Controllers.ControllerClasses.HistoryController;
import GUI.Utility.MoveBlockCommand;
import Utility.Position;

import java.awt.event.MouseEvent;
/**
 * A class used for handling mouse events.
 *
 * @invar The history controller of this gui block handler must be valid.
 *        | isValidGUIBlockHandler(blockHandler)
 * @invar The history controller of this history controller must be valid.
 *        | isValidHistoryController(historyController)
 *
 * @author Alpha-team
 */
public class MouseHandler {

    /**
     * Variable referring to the gui block handler.
     */
    private final GUIBlockHandler blockHandler;
    /**
     * Variable referring to the history controller.
     */
    private final HistoryController historyController;
    /**
     * Variable referring to the mouse position where a mouse interaction first happened.
     */
    private Position startPosition;

    /**
     * Create a new mouse handler with a given gui block handler and history controller.
     *
     * @param blockHandler The gui block handler for this mouse handler.
     * @param historyController The history controller for this mouse handler.
     *
     * @post The history controller is set to the given history controller.
     * @post the gui block handler is set to the given block handler.
     *
     * @throws IllegalArgumentException
     *         When the given gui block handler is not valid.
     * @throws IllegalArgumentException
     *         When the given history controller is not valid.
     */
    public MouseHandler(GUIBlockHandler blockHandler, HistoryController historyController) throws IllegalArgumentException{
        if (!isValidGUIBlockHandler(blockHandler)) {
            throw new IllegalArgumentException("The given block handler is not valid!");
        }
        if (!isValidHistoryController(historyController)) {
            throw new IllegalArgumentException("The given history controller is not valid!");
        }
        this.historyController = historyController;
        this.blockHandler = blockHandler;
    }

    /**
     * Check whether or not the given gui block handler is valid.
     *
     * @param blockHandler The gui block handler to check.
     *
     * @return True if and only if the given gui block handler is effective.
     */
    public static boolean isValidGUIBlockHandler(GUIBlockHandler blockHandler) {
        return blockHandler != null;
    }

    /**
     * Check whether or not the given history controller is valid.
     *
     * @param controller The history controller to check.
     *
     * @return True if and only if the given history controller is effective.
     */
    public static boolean isValidHistoryController(HistoryController controller) {
        return controller != null;
    }

    /**
     * Handle a new incoming mouse event with a given id, x and y coordinate.
     *
     * @param id The given mouse event id.
     * @param x The given mouse x-coordinate.
     * @param y The given mouse y-coordinate.
     *
     * @post The mouse start position is set when the mouse is pressed.
     *
     * @effect the history controller executes a block move command, if the mouse is released and blocks are being dragged.
     * @effect The block handler handles the mouse event if the mouse is in another state than released or no blocks are being dragged.
     */
    public void handleMouseEvent(int id, int x, int y) {
        if (id == MouseEvent.MOUSE_RELEASED && blockHandler.blocksAreDragged()) {
            historyController.execute(new MoveBlockCommand(startPosition, new Position(x, y), blockHandler));
        }
        else {
            if (id == MouseEvent.MOUSE_PRESSED) {
                startPosition = new Position(x, y);
            }
            blockHandler.handleMouseEvent(id, x, y);
        }
    }
}
