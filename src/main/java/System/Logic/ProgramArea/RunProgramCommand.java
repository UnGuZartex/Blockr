package System.Logic.ProgramArea;

public class RunProgramCommand extends ProgramCommand {

    protected RunProgramCommand(ProgramArea programArea) {
        super(programArea);
    }

    @Override
    protected void executeTask() {
        programArea.getProgram().executeStep(programArea.getGameWorld());
        programArea.notifyProgramState();
    }
}
