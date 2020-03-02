package Main.BlockStructure.Functionality;

public interface Condition {
    public boolean canBeEvaluated(); // Only if all sub-conditions are set
    public boolean evaluate();
}
