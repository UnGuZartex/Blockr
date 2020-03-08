package System.GameWorld;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CellTest {

    Cell cellBlank, cellWall, cellGoal;

    @BeforeEach
    void setUp() {
        cellBlank = new Cell(CellType.BLANK);
        cellWall =  new Cell(CellType.WALL);
        cellGoal = new Cell(CellType.GOAL);
    }

    @AfterEach
    void tearDown() {
        cellBlank = null;
        cellWall = null;
        cellGoal = null;
    }

    @Test
    void getCellType() {
        assertEquals(CellType.WALL, cellWall.getCellType());
        assertEquals(CellType.BLANK, cellBlank.getCellType());
        assertEquals(CellType.GOAL, cellGoal.getCellType());
    }
}