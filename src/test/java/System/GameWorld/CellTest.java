package System.GameWorld;

import org.junit.Assert;
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
        Assert.assertEquals(CellType.WALL, cellWall.getCellType());
        Assert.assertEquals(CellType.BLANK, cellBlank.getCellType());
        Assert.assertEquals(CellType.GOAL, cellGoal.getCellType());
    }
}