package System.BlockStructure.Functionality;

public abstract class ConditionalFunctionality implements IFunctionality {

    private boolean evaluation;

    protected void setEvaluation(boolean evaluation) {
        this.evaluation = evaluation;
    }

    public boolean getEvaluation() {
        return evaluation;
    }
}
