package Main.BlockStructure.Functionality;

public interface Condition {
    boolean canBeEvaluated(); // Only if all sub-conditions are set
    void evaluate(); // Set the evaluated
    boolean getEvaluation();
}
