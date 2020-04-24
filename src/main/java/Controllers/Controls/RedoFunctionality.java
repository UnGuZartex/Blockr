package Controllers.Controls;

import Controllers.ControllerClasses.ProgramController;
import GUI.Blocks.GUIBlock;

import java.awt.*;

public class RedoFunctionality implements ControlFunctionality{

    private GUIBlock previousBlock;

    private final ProgramController programController;

    public RedoFunctionality(ProgramController programController) {
        this.programController = programController;
    }
    @Override
    public void execute() {
        if (previousBlock != null) {
            previousBlock.setColor(Color.white);
        }
        programController.redoProgramStep();
        previousBlock = (GUIBlock) programController.getHightlightedBlock();
        if (previousBlock != null) {
            previousBlock.setColor(Color.red);
        }
    }
}
