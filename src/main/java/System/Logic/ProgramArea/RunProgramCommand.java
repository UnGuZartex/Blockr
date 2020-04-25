package System.Logic.ProgramArea;

/**
 * A class for program commands to run a program step.
 *
 * @author Alpha-team
 */
public class RunProgramCommand extends ProgramCommand {

    /**
     * Initialise a new run program command with given program area.
     *
     * @param programArea The program area for this run program command.
     *
     * @effect Calls the super constructor with given program area.
     */
    public RunProgramCommand(ProgramArea programArea) {
        super(programArea);
    }

    /**
     * Execute the task of this run program command.
     *
     * @effect A program step is run in the program area of this program command.
     */
    @Override
    protected void executeTask() {
        programArea.runProgramStep();
    }
}
