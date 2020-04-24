package System.Logic.ProgramArea;

public class RunProgramCommand extends ProgramCommand {

    public RunProgramCommand(ProgramArea programArea) {
        super(programArea);
    }

    @Override
    protected void executeTask() {
        programArea.runProgramStep();
        programArea.setGameWorldStartSnapshot(this.gameWorldSnapshot);
    }
}
