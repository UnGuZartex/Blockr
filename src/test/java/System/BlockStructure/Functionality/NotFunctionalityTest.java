package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.NotBlock;
import System.BlockStructure.Blocks.WallInFrontBlock;
import System.GameWorld.Cell;
import System.GameWorld.CellType;
import System.GameWorld.Direction;
import System.GameWorld.Level.Level;
import System.Logic.ProgramArea.ConnectionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class NotFunctionalityTest {

    private NotFunctionality functionality;
    private NotBlock notBlock, block1, block2;
    private WallInFrontBlock wallInFrontBlock;
    private ConnectionHandler connectionHandler;

    private Level levelUpOnBlankBeforeWall, levelDownOnGoalBeforeBlank,
            levelLeftOnGoalBeforeGoal, levelRightOnBlankBeforeWall;
    private Point pointUpOnBlankBeforeWall, pointDownOnGoalBeforeBlank,
            pointLeftOnGoalBeforeGoal, pointRightOnBlankBeforeWall;
    private Direction directionUpOnBlankBeforeWall, directionDownOnGoalBeforeBlank,
            directionLeftOnGoalBeforeGoal, directionRightOnBlankBeforeWall;
    private Cell[][] cellsUpOnBlankBeforeWall, cellsDownOnGoalBeforeBlank,
            cellsLeftOnGoalBeforeGoal, cellsRightOnBlankBeforeWall;

    private Level levelSimpleBlankUp, levelSimpleGoalDown, levelSimpleGoalLeft, levelSimpleBlankRight;
    private Point pointSimpleBlankUp, pointSimpleGoalDown, pointSimpleGoalLeft, pointSimpleBlankRight;
    private Direction directionSimpleBlankUp, directionSimpleGoalDown, directionSimpleGoalLeft, directionSimpleBlankRight;
    private Cell[][] cellsSimpleBlankUp, cellsSimpleGoalDown, cellsSimpleGoalLeft, cellsSimpleBlankRight;

    @BeforeEach
    void setUp() {
        wallInFrontBlock = new WallInFrontBlock();
        notBlock = new NotBlock();
        connectionHandler = new ConnectionHandler();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));

        functionality = new NotFunctionality();
        block1 = new NotBlock();
        block2 = new NotBlock();

        /* Simple field has only one cell */
        pointSimpleBlankUp = new Point(0,0);
        pointSimpleGoalDown = new Point(0,0);
        pointSimpleGoalLeft = new Point(0,0);
        pointSimpleBlankRight = new Point(0,0);
        pointUpOnBlankBeforeWall = new Point(1,1);
        pointDownOnGoalBeforeBlank = new Point(1,1);
        pointLeftOnGoalBeforeGoal = new Point(1,1);
        pointRightOnBlankBeforeWall = new Point(1,1);

        directionSimpleBlankUp = Direction.UP;
        directionSimpleGoalDown = Direction.DOWN;
        directionSimpleGoalLeft = Direction.LEFT;
        directionSimpleBlankRight = Direction.RIGHT;
        directionUpOnBlankBeforeWall = Direction.UP;
        directionDownOnGoalBeforeBlank = Direction.DOWN;
        directionLeftOnGoalBeforeGoal = Direction.LEFT;
        directionRightOnBlankBeforeWall = Direction.RIGHT;

        cellsSimpleBlankUp = new Cell[][] {
                { new Cell(CellType.BLANK) }
        };
        cellsSimpleGoalDown = new Cell[][] {
                { new Cell(CellType.GOAL) }
        };
        cellsSimpleGoalLeft = new Cell[][] {
                { new Cell(CellType.GOAL) }
        };
        cellsSimpleBlankRight = new Cell[][] {
                { new Cell(CellType.BLANK) }
        };
        // The matrix is mirrored as grid over the main diagonal
        cellsUpOnBlankBeforeWall = new Cell[][] {
                { new Cell(CellType.BLANK), new Cell(CellType.BLANK), new Cell(CellType.BLANK) },
                { new Cell(CellType.WALL), new Cell(CellType.BLANK), new Cell(CellType.BLANK) },
                { new Cell(CellType.BLANK), new Cell(CellType.BLANK), new Cell(CellType.BLANK) },
        };
        cellsDownOnGoalBeforeBlank = new Cell[][] {
                { new Cell(CellType.BLANK), new Cell(CellType.WALL), new Cell(CellType.BLANK) },
                { new Cell(CellType.WALL), new Cell(CellType.GOAL), new Cell(CellType.BLANK) },
                { new Cell(CellType.BLANK), new Cell(CellType.WALL), new Cell(CellType.BLANK) },
        };
        cellsLeftOnGoalBeforeGoal = new Cell[][] {
                { new Cell(CellType.BLANK), new Cell(CellType.GOAL), new Cell(CellType.BLANK) },
                { new Cell(CellType.BLANK), new Cell(CellType.GOAL), new Cell(CellType.BLANK) },
                { new Cell(CellType.BLANK), new Cell(CellType.BLANK), new Cell(CellType.BLANK) },
        };
        cellsRightOnBlankBeforeWall = new Cell[][] {
                { new Cell(CellType.BLANK), new Cell(CellType.BLANK), new Cell(CellType.BLANK) },
                { new Cell(CellType.BLANK), new Cell(CellType.BLANK), new Cell(CellType.BLANK) },
                { new Cell(CellType.BLANK), new Cell(CellType.WALL), new Cell(CellType.BLANK) },
        };

        levelSimpleBlankUp = new Level(pointSimpleBlankUp, directionSimpleBlankUp, cellsSimpleBlankUp);
        levelSimpleGoalDown = new Level(pointSimpleGoalDown, directionSimpleGoalDown, cellsSimpleGoalDown);
        levelSimpleGoalLeft = new Level(pointSimpleGoalLeft, directionSimpleGoalLeft, cellsSimpleGoalLeft);
        levelSimpleBlankRight = new Level(pointSimpleBlankRight, directionSimpleBlankRight, cellsSimpleBlankRight);
        levelUpOnBlankBeforeWall = new Level(pointUpOnBlankBeforeWall, directionUpOnBlankBeforeWall, cellsUpOnBlankBeforeWall);
        levelDownOnGoalBeforeBlank = new Level(pointDownOnGoalBeforeBlank, directionDownOnGoalBeforeBlank, cellsDownOnGoalBeforeBlank);
        levelLeftOnGoalBeforeGoal = new Level(pointLeftOnGoalBeforeGoal, directionLeftOnGoalBeforeGoal, cellsLeftOnGoalBeforeGoal);
        levelRightOnBlankBeforeWall = new Level(pointRightOnBlankBeforeWall, directionRightOnBlankBeforeWall, cellsRightOnBlankBeforeWall);
    }

    @AfterEach
    void tearDown() {
        functionality = null;
        notBlock = null;
        block1 = null;
        block2 = null;
        wallInFrontBlock = null;

        pointSimpleBlankUp = null;
        pointSimpleGoalDown = null;
        pointSimpleGoalLeft = null;
        pointSimpleBlankRight = null;
        pointUpOnBlankBeforeWall = null;
        pointDownOnGoalBeforeBlank = null;
        pointLeftOnGoalBeforeGoal = null;
        pointRightOnBlankBeforeWall = null;

        directionSimpleBlankUp = null;
        directionSimpleGoalDown = null;
        directionSimpleGoalLeft = null;
        directionSimpleBlankRight = null;
        directionUpOnBlankBeforeWall = null;
        directionDownOnGoalBeforeBlank = null;
        directionLeftOnGoalBeforeGoal = null;
        directionRightOnBlankBeforeWall = null;

        cellsSimpleBlankUp = null;
        cellsSimpleGoalDown = null;
        cellsSimpleGoalLeft = null;
        cellsSimpleBlankRight = null;
        cellsUpOnBlankBeforeWall = null;
        cellsDownOnGoalBeforeBlank = null;
        cellsLeftOnGoalBeforeGoal = null;
        cellsRightOnBlankBeforeWall = null;

        levelSimpleBlankUp = null;
        levelSimpleGoalDown = null;
        levelSimpleGoalLeft = null;
        levelSimpleBlankRight = null;
        levelUpOnBlankBeforeWall = null;
        levelDownOnGoalBeforeBlank = null;
        levelLeftOnGoalBeforeGoal = null;
        levelRightOnBlankBeforeWall = null;
    }

    @Test
    void setBlock() {
        assertNull(functionality.block);
        functionality.setBlock(block1);
        assertEquals(block1, functionality.block);
        functionality.setBlock(block2);
        assertEquals(block1, functionality.block);
    }

    @Test
    void getEvaluation() {
        assertFalse(notBlock.getFunctionality().getEvaluation());
        notBlock.getFunctionality().evaluate(levelUpOnBlankBeforeWall);
        assertFalse(notBlock.getFunctionality().getEvaluation());

        wallInFrontBlock = new WallInFrontBlock();
        notBlock = new NotBlock();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));
        assertFalse(notBlock.getFunctionality().getEvaluation());
        notBlock.getFunctionality().evaluate(levelDownOnGoalBeforeBlank);
        assertTrue(notBlock.getFunctionality().getEvaluation());

        wallInFrontBlock = new WallInFrontBlock();
        notBlock = new NotBlock();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));
        assertFalse(notBlock.getFunctionality().getEvaluation());
        notBlock.getFunctionality().evaluate(levelLeftOnGoalBeforeGoal);
        assertTrue(notBlock.getFunctionality().getEvaluation());

        wallInFrontBlock = new WallInFrontBlock();
        notBlock = new NotBlock();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));
        assertFalse(notBlock.getFunctionality().getEvaluation());
        notBlock.getFunctionality().evaluate(levelRightOnBlankBeforeWall);
        assertFalse(notBlock.getFunctionality().getEvaluation());

        wallInFrontBlock = new WallInFrontBlock();
        notBlock = new NotBlock();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));
        assertFalse(notBlock.getFunctionality().getEvaluation());
        notBlock.getFunctionality().evaluate(levelSimpleBlankUp);
        assertTrue(notBlock.getFunctionality().getEvaluation());

        wallInFrontBlock = new WallInFrontBlock();
        notBlock = new NotBlock();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));
        assertFalse(notBlock.getFunctionality().getEvaluation());
        notBlock.getFunctionality().evaluate(levelSimpleGoalDown);
        assertTrue(notBlock.getFunctionality().getEvaluation());

        wallInFrontBlock = new WallInFrontBlock();
        notBlock = new NotBlock();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));
        assertFalse(notBlock.getFunctionality().getEvaluation());
        notBlock.getFunctionality().evaluate(levelSimpleGoalLeft);
        assertTrue(notBlock.getFunctionality().getEvaluation());

        wallInFrontBlock = new WallInFrontBlock();
        notBlock = new NotBlock();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));
        assertFalse(notBlock.getFunctionality().getEvaluation());
        notBlock.getFunctionality().evaluate(levelSimpleBlankRight);
        assertTrue(notBlock.getFunctionality().getEvaluation());
    }

    @Test
    void evaluate() {
        notBlock.getFunctionality().evaluate(levelUpOnBlankBeforeWall);
        assertFalse(notBlock.getFunctionality().evaluation);

        wallInFrontBlock = new WallInFrontBlock();
        notBlock = new NotBlock();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));
        notBlock.getFunctionality().evaluate(levelDownOnGoalBeforeBlank);
        assertTrue(notBlock.getFunctionality().evaluation);

        wallInFrontBlock = new WallInFrontBlock();
        notBlock = new NotBlock();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));
        notBlock.getFunctionality().evaluate(levelLeftOnGoalBeforeGoal);
        assertTrue(notBlock.getFunctionality().evaluation);

        wallInFrontBlock = new WallInFrontBlock();
        notBlock = new NotBlock();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));
        notBlock.getFunctionality().evaluate(levelRightOnBlankBeforeWall);
        assertFalse(notBlock.getFunctionality().evaluation);

        wallInFrontBlock = new WallInFrontBlock();
        notBlock = new NotBlock();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));
        notBlock.getFunctionality().evaluate(levelSimpleBlankUp);
        assertTrue(notBlock.getFunctionality().evaluation);

        wallInFrontBlock = new WallInFrontBlock();
        notBlock = new NotBlock();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));
        notBlock.getFunctionality().evaluate(levelSimpleGoalDown);
        assertTrue(notBlock.getFunctionality().evaluation);

        wallInFrontBlock = new WallInFrontBlock();
        notBlock = new NotBlock();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));
        notBlock.getFunctionality().evaluate(levelSimpleGoalLeft);
        assertTrue(notBlock.getFunctionality().evaluation);

        wallInFrontBlock = new WallInFrontBlock();
        notBlock = new NotBlock();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));
        notBlock.getFunctionality().evaluate(levelSimpleBlankRight);
        assertTrue(notBlock.getFunctionality().evaluation);
    }
}