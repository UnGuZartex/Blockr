package System.Logic.ProgramArea;

import Controllers.Utility.ProgramEventManager;
import Controllers.ListenerInterfaces.ProgramListener;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.Utility.Snapshot;
import System.BlockStructure.Blocks.Block;
import System.Logic.CommandHistory;

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
    private final ArrayList<Program> programs = new ArrayList<>();

    /**
     * Variable referring to the program event manager.
     */
    private final ProgramEventManager observer = new ProgramEventManager();

    /**
     * Variable referring to the game world this program area operates on.
     */
    private final GameWorld gameWorld;

    /**
     * Variable referring to the initial snapshot of the game world.
     */
    private final Snapshot gameWorldStartSnapshot;

    /**
     * Variable referring to the command history of this program area.
     */
    private final CommandHistory history;

    /**
     * Initialise a new program area with given game world and command history.
     *
     * @param gameWorld The game world for this program area.
     * @param history The history for this program area.
     *
     * @post The game world of this program area is set to the given game world.
     * @post The history of this program area is set to the given history.
     * @post The initial game world snapshot is set to a new snapshot of the game world.
     */
    public ProgramArea(GameWorld gameWorld, CommandHistory history) {
        this.gameWorld = gameWorld;
        this.history = history;
        gameWorldStartSnapshot = gameWorld.createSnapshot();
    }

    /**
     * Return the game world attached to this program area.
     *
     * @return the game world attached to this program area.
     */
    public GameWorld getGameWorld() {
        return gameWorld;
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
        if (!hasExecutablePrograms()) {
            return null;
        }
        int index = 0;
        for (int i = 0; i < programs.size(); i++) {
            if (programs.get(i).getStartBlock().isIllegalExtraStartingBlock()) {
                index = i;
                break;
            }
        }
        return programs.get(index);
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
     * Checks whether or not this program area has executable programs.
     *
     * @return True if and only if exact 1 program in the program area has
     *         an illegal extra starting block, false in all other cases.
     */
    public boolean hasExecutablePrograms() {
        boolean passedMainProgram = false;
        for (Program program : programs) {
            if (program.getStartBlock().isIllegalExtraStartingBlock()) {
                if (passedMainProgram) {
                    return false;
                } else {
                    passedMainProgram = true;
                }
            }
        }
        return passedMainProgram;
    }

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
     * Get the next block in the program to execute.
     *
     * @return If there is only one program in the program are, then the current block
     *         of that program is returned, otherwise null is returned.
     */
    public Block getNextBlockInProgram() {
        if (hasExecutablePrograms()) {
            return getProgram().getCurrentBlock();
        }
        return null;
    }

    /**
     * Add a new program to this program area with given start block.
     *
     * @param startBlock The start block for the new program.
     *
     * @effect A new program with given start block is added to this program area if it
     *         is not connected through its main connector.
     * @effect If the given block is connected through its main connector, the highest
     *         block is added to the program area.
     *
     * @throws IllegalArgumentException
     *         If the given start block is not effective.
     */
    public void addProgram(Block startBlock) throws IllegalArgumentException {
        if (startBlock == null) {
            throw new IllegalArgumentException("The start block can't be null");
        }
        if (startBlock.getMainConnector() != null && startBlock.getMainConnector().isConnected()) {
            addHighestAsProgram(startBlock);
        } else if (programs.stream().noneMatch(p -> p.getStartBlock().equals(startBlock))) {
            programs.add(new Program(startBlock));
        }
    }

    /**
     * Add the highest block in the block structure of the given block as a program.
     *
     * @param block The given block.
     *
     * @throws IllegalArgumentException
     *         If the given block is not effective.
     */
    public void addHighestAsProgram(Block block) throws IllegalArgumentException {
        if (block == null) {
            throw new IllegalArgumentException("The block can't be null");
        }
        addProgram(getHighestBlock(block));
    }

    /**
     * Delete the program from this program area which has the given block
     * as starting block. If no such program exists, nothing happens.
     *
     * @param blockToDelete The starting block for the program to delete.
     *
     * @post The program with the given start block is deleted from this program area.
     */
    public void deleteProgram(Block blockToDelete) {
        programs.stream()
                .filter(p -> p.getStartBlock() == blockToDelete)
                .findFirst().ifPresent(programs::remove);
    }

    /**
     * Add a program command to the command history stack.
     *
     * @effect If there is no executable program in the program area, it is notified
     *         to the observer.
     * @effect If there is an executable program in this program area, and it is invalid, it is
     *         noticed to the observer.
     * @effect If there is an executable program in the program area which is valid and not finished,
     *         then is a new run program command executed in the command history with this
     *         program area.
     */
    public void addProgramRunCommand() {
        if (hasExecutablePrograms()) {
            Program program = getProgram();
            if (!program.isValidProgram()) {
                observer.notifyProgramInvalid();
            } else if (!program.isFinished()) {
                history.execute(new RunProgramCommand(this));
            }
        }
        else if (getAmountOfValidPrograms() > 1) {
            observer.notifyTooManyPrograms();
        }
        else {
            observer.notifyProgramInvalid();
        }
    }

    /**
     * Add a program reset command.
     *
     * @effect If there is an executable program in the program area which is valid and executing,
     *         a new program reset command is executed on the command history with
     *         this program area.
     * @effect Notify the program area listeners that the program is in its default state if
     *         there are no programs or too many programs in the program area.
     */
    public void addProgramResetCommand() {
        if (hasExecutablePrograms()) {
            Program program = getProgram();

            if (program.isValidProgram() && program.isResettable()) {
                history.execute(new ResetProgramCommand(this));
            }
        }
        else {
            observer.notifyProgramInDefaultState();
        }
    }

    /**
     * Reset the program area.
     *
     * @effect Each program in the programs list is reset.
     * @effect The initial snapshot of the game world is loaded.
     * @effect The observer notifies its listeners that the program has been reset.
     */
    public void resetProgramArea() {
        for (Program program : programs) {
            program.resetProgram();
        }
        gameWorld.loadSnapshot(gameWorldStartSnapshot);
        observer.notifyProgramInDefaultState();
    }

    /**
     * Run a step of the program.
     *
     * @effect A step in the program of this program area is done using the
     *         game world of this program area.
     * @effect The program state is notified.
     *
     * @throws IllegalStateException
     *         When there isn't 1 program in the program area.
     * @throws IllegalStateException
     *         When the program in the program area is not valid.
     */
    protected void runProgramStep() throws IllegalStateException {
        if (!hasExecutablePrograms()) {
            throw new IllegalStateException("A program step cannot be executed while there are multiple programs!");
        }
        if (!getProgram().isValidProgram()) {
            throw new IllegalStateException("A program step cannot be executed when the program is invalid!");
        }
        getProgram().executeStep(gameWorld);
        notifyProgramState();
    }

    /**
     * Notify the program state to the observer.
     *
     * @effect If the program in the program area is finished, its result is
     *         notified, otherwise the default state is notified.
     *
     * @throws IllegalStateException
     *         If There isn't 1 program in the program area.
     */
    protected void notifyProgramState() {
        if (!hasExecutablePrograms()) {
            throw new IllegalStateException("There is not just 1 program in the program area!");
        }
      
        Program program = getProgram();
        Result stepResult = program.getLastResult();
        if (program.isFinished()) {
            observer.notifyGameFinished(stepResult);
        }
        else {
            observer.notifyProgramInDefaultState();
        }
    }

    /**
     * Get the highest block in the block structure this block is connected to.
     *
     * @return This block if no block is higher, else the highest
     *         block of the block connected to the main connector.
     */
    private Block getHighestBlock(Block block) {
        if (block.isConnectedOnMain()) {
            return getHighestBlock(block.getMainConnector().getConnectedBlock());
        }
        return block;
    }

    /**
     * Get the total amount of valid programs.
     *
     * @return The total amount of programs which have an illegal extra start block.
     */
    private int getAmountOfValidPrograms() {
        int total = 0;
        for (Program program : programs) {
            if (program.getStartBlock().isIllegalExtraStartingBlock()) {
                total += 1;
            }
        }
        return total;
    }
}