package Controllers.Controls;

import Controllers.ControllerClasses.ProgramController;

public class ResetControlFunctionality implements ControlFunctionality {

    private final ProgramController programController;

    public ResetControlFunctionality(ProgramController programController) {
        this.programController = programController;
    }

    @Override
    public void execute() {
        programController.resetProgram();
    }
}
