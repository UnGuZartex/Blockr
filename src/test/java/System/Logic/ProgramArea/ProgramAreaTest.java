package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.MoveForwardBlock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgramAreaTest {

    ProgramArea pa0, pa1, pa2;
    Block start1, start2a, start2b;

    @BeforeEach
    void setUp() {
        pa0 = new ProgramArea();
        pa1 = new ProgramArea();
        pa2 = new ProgramArea();

        start1 = new MoveForwardBlock();
        start2a = new MoveForwardBlock();
        start2b =new MoveForwardBlock();

        pa1.addProgram(start1);
        pa2.addProgram(start2a);
        pa2.addProgram(start2b);
    }

    @AfterEach
    void tearDown() {
        pa0 = null;
        pa1 = null;
        pa2 = null;
    }

    @Test
    void getProgram() {
        assertNull(pa0.getProgram());
        assertEquals(start1, pa1.getProgram().getStartBlock());
        assertNull(pa2.getProgram());
    }

    @Test
    void addProgram_InvalidProgram() {
        assertThrows(IllegalArgumentException.class, () -> { pa1.addProgram(null); });
    }

    @Test
    void addProgram_ValidProgram() {
        Block block = new MoveForwardBlock();
        pa0.addProgram(block); // No error may be thrown
        assertEquals(block, pa0.getProgram().getStartBlock());
    }

    @Test
    void deleteProgram() {
        assertEquals(2, pa2.getAllBlocksCount());
        pa2.deleteProgram(start1);
        assertEquals(2, pa2.getAllBlocksCount());
        pa2.deleteProgram(start2a);
        assertEquals(1, pa2.getAllBlocksCount());
        pa2.deleteProgram(start2b);
        assertEquals(0, pa2.getAllBlocksCount());
    }

    @Test
    void getAllBlocksCount() {
        assertEquals(0, pa0.getAllBlocksCount());
        assertEquals(1, pa1.getAllBlocksCount());
        assertEquals(2, pa2.getAllBlocksCount());
    }
}