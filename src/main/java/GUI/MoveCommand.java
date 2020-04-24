package GUI;

import GUI.Components.GUIBlockHandler;
import GameWorldAPI.History.Snapshot;
import Utility.Command;

import java.awt.event.MouseEvent;

public class MoveCommand implements Command
{
    private final int x, y;
    private final GUIBlockHandler guiBlockHandler;
    private Snapshot GUIBlocksSnapshot;

    public MoveCommand(int x, int y, GUIBlockHandler guiBlockHandler) {
        this.x = x;
        this.y = y;
        this.guiBlockHandler = guiBlockHandler;
    }

    @Override
    public void execute() {
        this.GUIBlocksSnapshot = guiBlockHandler.createSnapshot();
        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, x, y);
    }

    @Override
    public void undo() {
        Snapshot snapshot = guiBlockHandler.createSnapshot();
        guiBlockHandler.loadSnapshot(this.GUIBlocksSnapshot);
        this.GUIBlocksSnapshot = snapshot;
    }

    @Override
    public void redo() {
        Snapshot snapshot = guiBlockHandler.createSnapshot();
        guiBlockHandler.loadSnapshot(this.GUIBlocksSnapshot);
        this.GUIBlocksSnapshot = snapshot;
    }
}
