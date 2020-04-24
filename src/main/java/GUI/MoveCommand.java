package GUI;

import GUI.Components.GUIBlockHandler;
import GameWorldAPI.History.Snapshot;
import Utility.Command;

import java.awt.event.MouseEvent;

public class MoveCommand implements Command
{
    int id, x, y;
    GUIBlockHandler guiBlockHandler;
    Snapshot snapshot;

    public MoveCommand(int x, int y, GUIBlockHandler guiBlockHandler) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.guiBlockHandler = guiBlockHandler;
    }

    @Override
    public void execute() {
        this.snapshot = guiBlockHandler.createSnapshot();
        //System.out.println("created snapshot " + this.snapshot);
        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, x, y);
    }

    @Override
    public void undo() {
        Snapshot snapshot = guiBlockHandler.createSnapshot();
        //System.out.println("created undo snapshot " + snapshot);
        guiBlockHandler.loadSnapshot(this.snapshot);
        //System.out.println("loaded undo snapshot " + this.snapshot);
        this.snapshot = snapshot;
    }

    @Override
    public void redo() {
        Snapshot snapshot = guiBlockHandler.createSnapshot();
        //System.out.println("created redo snapshot " + snapshot);
        guiBlockHandler.loadSnapshot(this.snapshot);
        //System.out.println("loaded redo snapshot " + this.snapshot);
        this.snapshot = snapshot;
    }
}
