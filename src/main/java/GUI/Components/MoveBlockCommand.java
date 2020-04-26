package GUI.Components;

import GameWorldAPI.History.Snapshot;
import Utility.Command;
import Utility.Position;

import java.awt.event.MouseEvent;

public class MoveBlockCommand implements Command
{
    private final Position start, end;
    private final GUIBlockHandler guiBlockHandler;
    private Snapshot GUIBlocksSnapshot;

    public MoveBlockCommand(Position start, Position end, GUIBlockHandler guiBlockHandler) {
        this.start = start;
        this.end = end;
        this.guiBlockHandler = guiBlockHandler;
    }

    @Override
    public void execute() {
        GUIBlocksSnapshot = guiBlockHandler.createSnapshot();
        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, start.getX(), start.getY());
        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, end.getX(), end.getY());
        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, end.getX(), end.getY());
    }

    @Override
    public void undo() {
        guiBlockHandler.loadSnapshot(GUIBlocksSnapshot);
    }
}
