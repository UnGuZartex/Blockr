package System.Logic.ProgramArea;

import Controllers.ProgramListener;
import Controllers.ProgramObserver;
import GameWorldAPI.GameWorld.Result;
import System.BlockStructure.Blocks.Block;

import java.util.ArrayList;

/**
 * A class for the program area. A program area has different programs in it.
 *
 * @author Alpha-team
 */
public class ProgramArea {

    /**
     * Variable referring to the programs in this program area.
     */
    private ArrayList<Program> programs = new ArrayList<>();

    /**
     * Variabele referring to the observer of this class.
     * This observer will notify listeners about the events of the program.
     */
    private ProgramObserver observer = new ProgramObserver();

    /**
     * Unsubscribe a given program listener from the program observer
     *
     * @param listener The given listener
     *
     * @effect the given listener is subscribed to the program observer
     */
    public void subscribe(ProgramListener listener) {
        observer.subscribe(listener);
    }

    /**
     * Subscribe a given program listener to the program observer
     *
     * @param listener The given listener
     *
     * @effect the given listener is unsubscribed from the program observer
     */
    public void unsubscribe(ProgramListener listener) {
        observer.unsubscribe(listener);
    }

    /**
     * Get the program in the program area.
     *
     * @return If there is only one program in this program area,
     *         then it is returned. In all other cases, there is an
     *         invalid number of programs in the program area and
     *         is null returned.
     */
    public Program getProgram() {
        if(programs.size() == 1){
            return programs.get(0);
        } else {
            return null;
        }
    }

    /**
     * Execute a program step if possible.
     *
     * @effect If there is only one program in this program area and that program is valid,
     *         then the current program executes for one step.
     * @effect The observer notifies its listeners that the amount of programs is too high
     *         in the program area if that's the case.
     * @effect The observer notifies its listeners that the program is invalid
     *         if there's only one program in the program area and if it's invalid.
     * @effect The observer notifies its listeners whether the game is won or not
     *         when the program is fully finished executing.
     */
    /**
     * TODO commentaar
     */
    public void runProgramStep() {
        if (programs.size() == 1) {
            Program program = programs.get(0);

            if (program.isValidProgram()) {
                Result stepResult = program.executeStep();

                if (program.isFinished()) {
                    observer.notifyGameFinished(stepResult);
                }
            }
            else {
                observer.notifyProgramInvalid();
            }
            observer.notifyExecuting(program.isExecuting());
        }
        else if (programs.size() > 1) {
            observer.notifyTooManyPrograms();
        }
    }

    /**
     * Reset all programs to their initial state.
     *
     * @effect Each program in the programs list is reset.
     * @effect The observer notifies its listeners that the program has been reset.
     */
    public void resetProgram() {
        for (Program program : programs) {
            program.resetProgram();
        }

        observer.notifyProgramReset();
    }

    public void undoProgram() {
        if (programs.size() == 1) {
            Program program = programs.get(0);

            if (program.isValidProgram()) {
                Result stepResult = program.undoProgram();

                if (program.isFinished()) {
                    observer.notifyGameFinished(stepResult);
                }
            }
            else {
                observer.notifyProgramInvalid();
            }
            observer.notifyProgramReset();
            observer.notifyExecuting(program.isExecuting());
        }
        else if (programs.size() > 1) {
            observer.notifyTooManyPrograms();
        }


    }

    public void redoProgram() {
        if (programs.size() == 1) {
            Program program = programs.get(0);

            if (program.isValidProgram()) {
                Result stepResult = program.redoProgram();

                if (program.isFinished()) {
                    observer.notifyGameFinished(stepResult);
                }
                observer.notifyExecuting(program.isExecuting());
            }
            else {
                observer.notifyProgramInvalid();
            }
        }
        else if (programs.size() > 1) {
            observer.notifyTooManyPrograms();
        }
    }

    /**
     * Add a new program to this program area with given start block.
     *
     * @param startBlock The start block for the new program.
     *
     * @post A new program with given start block is added to this program area.
     *
     * @throws IllegalArgumentException
     *         If the given start block is not effective.
     */
    public void addProgram(Block startBlock) {
        if (startBlock == null) {
            throw new IllegalArgumentException("Block can't be null");
        }
        if (programs.stream().noneMatch(p -> p.getStartBlock().equals(startBlock))) {
            programs.add(new Program(startBlock));
        }
    }

    /**
     * Delete the program from this program area which has the given block
     * as starting block. If no such program exists, nothing happens.
     *
     * @param blockToDelete The starting block for the program to delete.
     *
     * @post The program with the given startblock is deleted from this program area.
     */
    public void deleteProgram(Block blockToDelete) {
        programs.stream()
                .filter(p -> p.getStartBlock() == blockToDelete)
                .findFirst().ifPresent(toDelete -> programs.remove(toDelete));
    }

    /**
     * Get the total number of blocks in this program area.
     *
     * @return The sum of the total number of blocks in each program
     *         in this program area.
     */
    public int getAllBlocksCount() {
        int sum = 0;
        for (Program program : programs) {
            sum += program.getSize();
        }
        return sum;
    }

    /**
     * Add the highest block in the block structure of the given block as a program.
     *
     * @param block The given block.
     */
    public void addHighestAsProgram(Block block) {
        addProgram(getHighestBlock(block));
    }

    /**
     * Get the highest block in the block structure this block is connected to.
     *
     * @return This block if no block is higher, else the highest
     *         block of the block connected to the main connector.
     */
    private Block getHighestBlock(Block block) {
        if (block.getMainConnector().isConnected()) {
            return getHighestBlock(block.getMainConnector().getConnectedBlock());
        }
        else {
            return block;
        }
    }

    public boolean isExecuted() {
        if (programs.size() == 1) {
            Program program = programs.get(0);
            return program.isExecuting();
        }
        return false;
    }
}
