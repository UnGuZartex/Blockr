package System.Logic.ProgramArea;

public class ResetProgramCommand extends ProgramCommand {

    protected ResetProgramCommand(ProgramArea programArea) {
        super(programArea);
    }

    @Override
    protected void executeTask() {
        programArea.getProgram().resetProgram();
    }
}
