package Utility;

/**
 * Interface representing a command that can be executed and undone.
 */
public interface Command {

    /**
     * Execute this command.
     */
    void execute();

    /**
     * Undo this command.
     */
    void undo();
}
