package Controllers.Controls;

import Controllers.ControllerClasses.ProgramController;
import GUI.Panel.GameWorldPanel;

public class ResetControlFunctionality implements ControlFunctionality {

    private final ProgramController programController;
    private final GameWorldPanel gameWorldPanel;

    public ResetControlFunctionality(ProgramController programController, GameWorldPanel gameWorldPanel) {
        this.programController = programController;
        this.gameWorldPanel = gameWorldPanel;
    }

    @Override
    public void execute() {
        programController.resetGameWorld();
        gameWorldPanel.resetGameText();
    }
}
