package System.Logic.ProgramArea;

import GameWorldAPI.History.Snapshot;
import Utility.Command;

/**
 * A class for program commands.
 *
 * @author Alpha-team
 */
public abstract class ProgramCommand implements Command {

    /**
     * Variable referring to the program area this program command executes on.
     */
    protected final ProgramArea programArea;

    /**
     * Variable referring to the snapshot of the program to keep track of.
     */
    private Snapshot programSnapShot;

    /**
     * Variable referring to the snapshot of the game world to keep track of.
     */
    private Snapshot gameWorldSnapshot;

    /**
     * Initialse a new program command with given program area.
     *
     * @param programArea The program are for this program command.
     *
     * @post The program area of this program command is set to the given
     *       program area.
     */
    protected ProgramCommand(ProgramArea programArea) {
        this.programArea = programArea;
    }

    /**
     * Execute this program command.
     *
     * @effect Create a snapshot the program in the program area.
     * @effect Create a snapshot the game world in the program area.
     * @effect Execute the task of this program area.
     *
     * @throws IllegalStateException
     *         When the program area doesn't have 1 valid program.
     */
    @Override
    public void execute() throws IllegalStateException {
        if (programArea.getProgram() == null || !programArea.getProgram().isValidProgram()) {
            throw new IllegalStateException("There isn't 1 valid program in the program area!");
        }
        programSnapShot = programArea.getProgram().createSnapshot();
        gameWorldSnapshot = programArea.getGameWorld().createSnapshot();
        executeTask();
    }

    /**
     * Undo this program command.
     *
     * @effect Load the snapshot of the program in the program of the program area.
     * @effect Load the snapshot of the game world in the game world of the program area.
     * @effect Notify the program area that the program state has changed.
     */
    @Override
    public void undo() {
        programArea.getProgram().loadSnapshot(programSnapShot);
        programArea.getGameWorld().loadSnapshot(gameWorldSnapshot);
        programArea.notifyProgramState();
    }

    /**
     * Execute the task of this program command.
     */
    protected abstract void executeTask();
}
