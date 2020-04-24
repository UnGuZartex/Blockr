package System.Logic.ProgramArea;

public class ResetProgramCommand extends ProgramCommand {

    public ResetProgramCommand(ProgramArea programArea) {
        super(programArea);
    }

    @Override
    protected void executeTask() {
        programArea.getProgram().resetProgram();
        programArea.resetGameWorld();
        programArea.notifyProgramState();
    }
}
