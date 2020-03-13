package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;

import java.util.ArrayList;

public class ProgramArea {
    private ArrayList<Program> programs = new ArrayList<>();


    public void deleteProgram(Block blockToDelete) {
        programs.stream().
                filter(p -> p.getStartBlock() == blockToDelete).
                findFirst().ifPresent(toDelete -> programs.remove(toDelete));
    }

    public Program getProgram() {
        if(programs.size() == 1){
            return programs.get(0);
        }
        else{
            return null;
        }
    }

    public int getAllBlocksCount() {
        int sum = 0;
        for (Program program : programs) {
            sum += program.getSize();
        }
        return sum;
    }

    public void addProgram(Block startBlock) {
        if (startBlock == null) {
            throw new IllegalArgumentException("Block can't be null");
        }
        programs.add(new Program(startBlock));
    }
}
