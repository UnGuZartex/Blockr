package BlockStructure.Functionality;

public abstract class ConditionalFunctionality implements Functionality {

    private boolean evaluation;

    protected void setEvaluation(boolean evaluation) {
        this.evaluation = evaluation;
    }

    public boolean getEvaluation() {
        return evaluation;
    }
}
