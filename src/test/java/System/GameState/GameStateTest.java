package System.GameState;

import System.GameWorld.Cell;
import System.GameWorld.CellType;
import System.GameWorld.Direction;
import System.GameWorld.Level.Level;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    Level level1, level2;
    int nbBlocks1, nbBlocks2;

    @BeforeEach
    void setUp() {
        nbBlocks1 = 2;
        nbBlocks2 = 5;
        level1 = new Level(new Point(0,0), Direction.UP, new Cell[][] {{new Cell(CellType.BLANK)}});
        level2 = new Level(new Point(0,0), Direction.UP, new Cell[][] {{new Cell(CellType.BLANK)}});
        GameState.setCurrentLevel(level1);
        GameState.setMaxAmountOfBlocks(nbBlocks1);
    }

    @AfterEach
    void TearDown() {
        level1 = null;
        level2 = null;
    }

    @Test
    void getCurrentLevel() {
        assertEquals(level1, GameState.getCurrentLevel());
    }

    @Test
    void setCurrentLevel() {
        GameState.setCurrentLevel(level2);
        assertEquals(level2, GameState.getCurrentLevel());
    }

    @Test
    void getMaxAmountOfBlocks() {
        assertEquals(nbBlocks1, GameState.getMaxAmountOfBlocks());

    }
    @Test
    void setMaxAmountOfBlocks() {
        GameState.setMaxAmountOfBlocks(nbBlocks2);
        assertEquals(nbBlocks2, GameState.getMaxAmountOfBlocks());
    }
}