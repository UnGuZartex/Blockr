package Controllers.Controls;

import Controllers.ProgramController;
import GUI.Blocks.GUIBlock;

import java.awt.*;

public class ProgramStepCommand implements ControlFunctionality {

    private GUIBlock previousBlock;
    private final ProgramController programController;

    public ProgramStepCommand(ProgramController programController) {
        this.programController = programController;
    }

    @Override
    public void execute() {
        if (previousBlock != null) {
            previousBlock.setColor(Color.white);
        }

        programController.runProgramStep();
        previousBlock = programController.getHightlightedBlock();
        if (previousBlock != null) {
            previousBlock.setColor(Color.red);
        }

    }
}
