package Controllers.Controls;


public class Control {

    private final int shortCutCode;
    private final ControlFunctionality controlFunctionality;

    public Control(int shortCutCode, ControlFunctionality controlFunctionality) {
        this.shortCutCode = shortCutCode;
        this.controlFunctionality = controlFunctionality;
    }

    public boolean isClicked(int keyCode) {
        return keyCode == shortCutCode;
    }

    public void onClick() {
        controlFunctionality.execute();
    }
}
