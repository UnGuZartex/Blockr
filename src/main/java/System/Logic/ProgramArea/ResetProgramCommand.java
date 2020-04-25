package System.Logic.ProgramArea;

/**
 * A class for program commands to reset the program.
 *
 * @author Alpha-team
 */
public class ResetProgramCommand extends ProgramCommand {

    /**
     * Initialise a new reset program command with given program area.
     *
     * @param programArea The program area for this reset program command.
     *
     * @effect Calls the super constructor with given program area.
     */
    public ResetProgramCommand(ProgramArea programArea) {
        super(programArea);
    }

    /**
     * Execute the task of this reset program command.
     *
     * @effect The program area of this program command is reset.
     */
    @Override
    protected void executeTask() {
        programArea.resetProgramArea();
    }
}
