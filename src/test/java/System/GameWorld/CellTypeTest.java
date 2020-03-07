package System.GameWorld;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CellTypeTest {

    private CellType blank, wall, goal;

    @BeforeEach
    void setUp() {
        blank = CellType.BLANK;
        wall = CellType.WALL;
        goal = CellType.GOAL;
    }

    @AfterEach
    void tearDown() {
        blank = null;
        wall = null;
        goal = null;
    }

    @Test
    void canWalkOn() {
        Assert.assertTrue(blank.canWalkOn());
        Assert.assertFalse(wall.canWalkOn());
        Assert.assertTrue(goal.canWalkOn());
    }

    @Test
    void isWin() {
        Assert.assertFalse(blank.isWin());
        Assert.assertFalse(wall.isWin());
        Assert.assertTrue(goal.isWin());
    }
}