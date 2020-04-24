package System.Logic.ProgramArea;

import GameWorldAPI.History.Snapshot;
import Utility.Command;

public abstract class ProgramCommand implements Command {

    ProgramArea programArea;
    Snapshot programSnapShot;
    Snapshot gameWorldSnapshot;

    protected ProgramCommand(ProgramArea programArea) {
        this.programArea = programArea;
    }

    @Override
    public void execute() {
        programSnapShot = programArea.getProgram().createSnapshot();
        gameWorldSnapshot = programArea.getGameWorld().createSnapshot();
        executeTask();
    }

    @Override
    public void undo() {
        programArea.getProgram().loadSnapshot(programSnapShot);
        programArea.getGameWorld().loadSnapshot(gameWorldSnapshot);
        programArea.notifyProgramState();
    }

    @Override
    public void redo() {
        execute();
    }

    protected abstract void executeTask();
}
