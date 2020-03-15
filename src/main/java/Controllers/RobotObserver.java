package Controllers;

import System.GameWorld.Direction;
import Utility.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * A class used as an observer for notifying its listeners about
 * events of the robot state.
 *
 * @author Alpha-team
 */
public class RobotObserver {

    /**
     * Variable referring to the listeners of this robot state observer
     */
    private List<RobotListener> listeners = new ArrayList<>();

    /**
     * Subscribe a given robot listener to this observer
     *
     * @param listener the given robot listener
     *
     * @post The given listener is added to the current listeners.
     */
    public void subscribe(RobotListener listener) {
        listeners.add(listener);
    }

    /**
     * Unsubscribe a given robot listener from this observer
     * @param listener the given robot listener
     *
     * @post The given listener is removed from the current listeners.
     */
    public void unsubscribe(RobotListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify the listeners that the robot has moved to the given position.
     *
     * @param newPosition The new robot position
     *
     * @effect The listeners are notified about the new robot position.
     */
    public void notifyRobotMoved(Position newPosition) {
        for (RobotListener listener : listeners) {
            listener.onRobotMoved(newPosition);
        }
    }

    /**
     * Notify the listeners that the robot has turned to the given direction.
     *
     * @param newDirection The new robot direction
     *
     @effect The listeners are notified about the new robot direction.
     */
    public void notifyRobotChangedDirection(Direction newDirection) {
        for (RobotListener listener : listeners) {
            listener.onRobotChangedDirection(newDirection);
        }
    }
}
