package Utility;

/**
 * Interface representing a command that can be executed and undone.
 */
public interface Command {
    void execute();
    void undo();
}
