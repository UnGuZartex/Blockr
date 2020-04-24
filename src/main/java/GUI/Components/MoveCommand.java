package GUI.Components;

import GUI.Components.GUIBlockHandler;
import GameWorldAPI.History.Snapshot;
import Utility.Command;
import Utility.Position;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MoveCommand implements Command
{
    private Position start, end;
    private final GUIBlockHandler guiBlockHandler;
    private Snapshot GUIBlocksSnapshot;

    public MoveCommand(Position start, Position end, GUIBlockHandler guiBlockHandler) {
        this.start = start;
        this.end = end;
        this.guiBlockHandler = guiBlockHandler;
    }

    @Override
    public void execute() {
        this.GUIBlocksSnapshot = guiBlockHandler.createSnapshot();
        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, start.getX(), start.getY());
        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, end.getX(), end.getY());
        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, end.getX(), end.getY());
    }

    @Override
    public void undo() {
        Snapshot snapshot = guiBlockHandler.createSnapshot();
        guiBlockHandler.loadSnapshot(this.GUIBlocksSnapshot);
        this.GUIBlocksSnapshot = snapshot;
    }
}
