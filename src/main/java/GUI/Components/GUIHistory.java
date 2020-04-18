package GUI.Components;

import Utility.Position;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Stack;

public class GUIHistory {

    private final GUIBlockHandler handler;

    //An entry is defined as pressedposition, releasedposition
    private final Stack<Map.Entry<Position, Position>>  undoMovementStack = new Stack<>();
    private final Stack<Map.Entry<Position, Position>>  redoMovementStack = new Stack<>();


    public GUIHistory(GUIBlockHandler handler) {
        this.handler = handler;
    }

    public void newMovement(Map.Entry<Position, Position> position) {
        undoMovementStack.push(position);
        redoMovementStack.clear();
    }

    public void undo() {
        handleUndoRedo(undoMovementStack, redoMovementStack);
    }


    public void redo() {
        handleUndoRedo(redoMovementStack, undoMovementStack);
    }

    private void handleUndoRedo(Stack<Map.Entry<Position, Position>> FunctionalStack, Stack<Map.Entry<Position, Position>> AddingStack) {
        if (FunctionalStack.isEmpty()) return;
        Map.Entry<Position, Position> entry = FunctionalStack.pop();
        Position toRelease = entry.getKey();
        Position toPress = entry.getValue();
        AddingStack.push(new AbstractMap.SimpleEntry<>(toPress, toRelease));
        handler.handleMousePressed(toPress.getX(), toPress.getY());
        handler.handleMouseDragged(toRelease.getX(), toRelease.getY());
        handler.handleMouseReleased();
    }
}
