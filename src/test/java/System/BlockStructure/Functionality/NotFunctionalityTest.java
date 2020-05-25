package System.BlockStructure.Functionality;

import GameWorld.Cell;
import GameWorld.CellType;
import GameWorld.Grid;
import GameWorld.Level;
import GameWorldUtility.Predicates.WallInFrontPredicate;
import RobotCollection.Robot.Direction;
import RobotCollection.Robot.Robot;
import RobotCollection.Utility.GridPosition;
import System.BlockStructure.Blocks.NotBlock;
import System.BlockStructure.Blocks.OperationalBlock;
import System.BlockStructure.Blocks.PredicateBlock;
import System.Logic.ProgramArea.Handlers.ConnectionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class NotFunctionalityTest {


    private NotFunctionality functionality;
    private OperationalBlock notBlock, block1, block2;
    private PredicateBlock wallInFrontBlock;

    private Level levelUpOnBlankBeforeWall, levelDownOnGoalBeforeBlank,
            levelLeftOnGoalBeforeGoal, levelRightOnBlankBeforeWall;
    private GridPosition PositionUpOnBlankBeforeWall, PositionDownOnGoalBeforeBlank,
            PositionLeftOnGoalBeforeGoal, PositionRightOnBlankBeforeWall;
    private Direction directionUpOnBlankBeforeWall, directionDownOnGoalBeforeBlank,
            directionLeftOnGoalBeforeGoal, directionRightOnBlankBeforeWall;
    private Cell[][] cellsUpOnBlankBeforeWall, cellsDownOnGoalBeforeBlank,
            cellsLeftOnGoalBeforeGoal, cellsRightOnBlankBeforeWall;

    private Level levelSimpleBlankUp, levelSimpleGoalDown, levelSimpleGoalLeft, levelSimpleBlankRight;
    private GridPosition PositionSimpleBlankUp, PositionSimpleGoalDown, PositionSimpleGoalLeft, PositionSimpleBlankRight;
    private Direction directionSimpleBlankUp, directionSimpleGoalDown, directionSimpleGoalLeft, directionSimpleBlankRight;
    private Cell[][] cellsSimpleBlankUp, cellsSimpleGoalDown, cellsSimpleGoalLeft, cellsSimpleBlankRight;

    @BeforeEach
    void setUp() {

        /* Simple field has only one cell */
        PositionSimpleBlankUp = new GridPosition(0,0);
        PositionSimpleGoalDown = new GridPosition(0,0);
        PositionSimpleGoalLeft = new GridPosition(0,0);
        PositionSimpleBlankRight = new GridPosition(0,0);
        PositionUpOnBlankBeforeWall = new GridPosition(1,1);
        PositionDownOnGoalBeforeBlank = new GridPosition(1,1);
        PositionLeftOnGoalBeforeGoal = new GridPosition(1,1);
        PositionRightOnBlankBeforeWall = new GridPosition(1,1);

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

        levelSimpleBlankUp = new Level(new Robot(PositionSimpleBlankUp, directionSimpleBlankUp), new Grid(cellsSimpleBlankUp));
        levelSimpleGoalDown = new Level(new Robot(PositionSimpleGoalDown, directionSimpleGoalDown), new Grid(cellsSimpleGoalDown));
        levelSimpleGoalLeft = new Level(new Robot(PositionSimpleGoalLeft, directionSimpleGoalLeft), new Grid(cellsSimpleGoalLeft));
        levelSimpleBlankRight = new Level(new Robot(PositionSimpleBlankRight, directionSimpleBlankRight), new Grid(cellsSimpleBlankRight));
        levelUpOnBlankBeforeWall = new Level(new Robot(PositionUpOnBlankBeforeWall, directionUpOnBlankBeforeWall), new Grid(cellsUpOnBlankBeforeWall));
        levelDownOnGoalBeforeBlank = new Level(new Robot(PositionDownOnGoalBeforeBlank, directionDownOnGoalBeforeBlank), new Grid(cellsDownOnGoalBeforeBlank));
        levelLeftOnGoalBeforeGoal = new Level(new Robot(PositionLeftOnGoalBeforeGoal, directionLeftOnGoalBeforeGoal), new Grid(cellsLeftOnGoalBeforeGoal));
        levelRightOnBlankBeforeWall = new Level(new Robot(PositionRightOnBlankBeforeWall, directionRightOnBlankBeforeWall), new Grid(cellsRightOnBlankBeforeWall));


        wallInFrontBlock = new PredicateBlock(new PredicateFunctionality(new WallInFrontPredicate()));

        notBlock = new NotBlock();
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.connect(wallInFrontBlock, notBlock.getSubConnectorAt(0));

        functionality = new NotFunctionality();
        block1 = new NotBlock();
        block2 = new NotBlock();
    }

    @AfterEach
    void tearDown() {
        functionality = null;
        notBlock = null;
        block1 = null;
        block2 = null;
        wallInFrontBlock = null;

        PositionSimpleBlankUp = null;
        PositionSimpleGoalDown = null;
        PositionSimpleGoalLeft = null;
        PositionSimpleBlankRight = null;
        PositionUpOnBlankBeforeWall = null;
        PositionDownOnGoalBeforeBlank = null;
        PositionLeftOnGoalBeforeGoal = null;
        PositionRightOnBlankBeforeWall = null;

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
    void evaluate() {
        assertFalse(notBlock.getFunctionality().getEvaluation());
        notBlock.getFunctionality().evaluate(levelRightOnBlankBeforeWall);
        assertFalse(notBlock.getFunctionality().getEvaluation());

        assertFalse(notBlock.getFunctionality().getEvaluation());
        notBlock.getFunctionality().evaluate(levelLeftOnGoalBeforeGoal);
        assertTrue(notBlock.getFunctionality().getEvaluation());
    }
}