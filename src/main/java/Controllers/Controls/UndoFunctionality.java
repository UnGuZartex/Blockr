package Controllers.Controls;


import Controllers.ControllerClasses.ProgramController;
import GUI.Blocks.GUIBlock;
import GUI.Panel.GameWorldPanel;

import java.awt.*;

public class UndoFunctionality implements ControlFunctionality {

    private GUIBlock previousBlock;

    private final ProgramController programController;
    private final GameWorldPanel gameWorldPanel;

    public UndoFunctionality(ProgramController programController, GameWorldPanel gameWorldPanel) {
        this.programController = programController;
        this.gameWorldPanel = gameWorldPanel;
    }
    @Override
    public void execute() {
        if (previousBlock != null) {
            previousBlock.setColor(Color.white);
        }
        programController.undoProgramStep();
        gameWorldPanel.resetGameText();
        previousBlock = (GUIBlock) programController.getHightlightedBlock();
        if (previousBlock != null) {
            previousBlock.setColor(Color.red);
        }
    }
}
