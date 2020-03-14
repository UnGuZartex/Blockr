package System.Logic.ProgramArea;

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
        programs.add(new Program(startBlock));
    }

    /**
     * Delete the program from this program area which has the given block
     * as starting block. If no such program exists, nothing happens.
     *
     * @post The program with the given startblock is deleted from this program area.
     *
     * @param blockToDelete The starting block for the program to delete.
     */
    public void deleteProgram(Block blockToDelete) {
        programs.stream().
                filter(p -> p.getStartBlock() == blockToDelete).
                findFirst().ifPresent(toDelete -> programs.remove(toDelete));
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
}
