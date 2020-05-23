package GUI.Components;

import Controllers.ControllerClasses.HistoryController;
import Utility.Position;

import java.awt.event.MouseEvent;
/**
 * A class used for handling mouse events.
 *
 * @invar The history controller of this gui block handler may not be null.
 *        | historyController != null
 */
public class MouseHandler {

    /**
     * Variable referring to the gui block handler.
     */
    private GUIBlockHandler blockHandler;

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
     * @post The history controller is set to the given history controller.
     * @post the gui block handler is set to the given block handler.
     *
     * @throws IllegalArgumentException
     *         When the given history controller is not effective.
     */
    public MouseHandler(GUIBlockHandler blockHandler, HistoryController historyController) {

        if (historyController == null) {
            throw new IllegalArgumentException("The given history controller is not effective!");
        }

        this.historyController = historyController;
        this.blockHandler = blockHandler;
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
     * @effect the history controller executes a block move command, if the mouse is released.
     * @effect The block handler handles the mouse event if the mouse is in another state than released.
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
