package System.BlockStructure.Blocks;

import System.GameWorld.Cell;
import System.GameWorld.CellType;
import System.GameWorld.Direction;
import System.GameWorld.Level.Level;
import System.Logic.ProgramArea.ConnectionHandler;
import Utility.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhileBlockTest {

    WhileBlock while1, while2, while3, while4, while5;
    FunctionalBlock func1, func11, func2, func3, func1Under, func5Under;
    ConditionalBlock cond1, cond3, cond5;

    WhileBlock whileBlock1, innerWhileBlock1, whileBlock2, outerWhileBlock2;
    ConditionalBlock condBlock1, condBlock2;

    private Level levelUpOnBlankBeforeWall, levelDownOnGoalBeforeBlank,
            levelLeftOnGoalBeforeGoal, levelRightOnBlankBeforeWall;
    private Position PositionUpOnBlankBeforeWall, PositionDownOnGoalBeforeBlank,
            PositionLeftOnGoalBeforeGoal, PositionRightOnBlankBeforeWall;
    private Direction directionUpOnBlankBeforeWall, directionDownOnGoalBeforeBlank,
            directionLeftOnGoalBeforeGoal, directionRightOnBlankBeforeWall;
    private Cell[][] cellsUpOnBlankBeforeWall, cellsDownOnGoalBeforeBlank,
            cellsLeftOnGoalBeforeGoal, cellsRightOnBlankBeforeWall;

    @BeforeEach
    void setUp() {
        while1 = new WhileBlock();
        while2 = new WhileBlock();
        while3 = new WhileBlock();
        while4 = new WhileBlock();
        while5 = new WhileBlock();
        whileBlock1 = new WhileBlock();
        innerWhileBlock1 = new WhileBlock();
        whileBlock2 =new WhileBlock();
        outerWhileBlock2 = new WhileBlock();

        func1 = new MoveForwardBlock();
        func11 =new MoveForwardBlock();
        func2 = new MoveForwardBlock();
        func3 =new MoveForwardBlock();
        func1Under =new MoveForwardBlock();
        func5Under = new MoveForwardBlock();

        cond1 = new WallInFrontBlock();
        cond3 = new WallInFrontBlock();
        cond5 = new WallInFrontBlock();
        condBlock1 = new WallInFrontBlock();
        condBlock2 = new WallInFrontBlock();

        ConnectionHandler handler = new ConnectionHandler();
        handler.connect(while1, func3.getSubConnectorAt(0));
        handler.connect(func11, func1.getSubConnectorAt(0));
        handler.connect(func1, while1.getCavitySubConnector());
        handler.connect(cond1, while1.getConditionalSubConnector());
        handler.connect(func1Under, while1.getSubConnectorAt(0));
        handler.connect(func2, while2.getCavitySubConnector());
        handler.connect(cond3, while3.getConditionalSubConnector());
        handler.connect(cond5, while5.getConditionalSubConnector());
        handler.connect(func5Under, while5.getSubConnectorAt(0));
        handler.connect(innerWhileBlock1, whileBlock1.getCavitySubConnector());
        handler.connect(condBlock1, whileBlock1.getConditionalSubConnector());
        handler.connect(outerWhileBlock2, whileBlock2.getSubConnectorAt(0));
        handler.connect(condBlock2, whileBlock2.getConditionalSubConnector());

        PositionUpOnBlankBeforeWall = new Position(1,1);
        PositionDownOnGoalBeforeBlank = new Position(1,1);
        PositionLeftOnGoalBeforeGoal = new Position(1,1);
        PositionRightOnBlankBeforeWall = new Position(1,1);

        directionUpOnBlankBeforeWall = Direction.UP;
        directionDownOnGoalBeforeBlank = Direction.DOWN;
        directionLeftOnGoalBeforeGoal = Direction.LEFT;
        directionRightOnBlankBeforeWall = Direction.RIGHT;

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

        levelUpOnBlankBeforeWall = new Level(PositionUpOnBlankBeforeWall, directionUpOnBlankBeforeWall, cellsUpOnBlankBeforeWall);
        levelDownOnGoalBeforeBlank = new Level(PositionDownOnGoalBeforeBlank, directionDownOnGoalBeforeBlank, cellsDownOnGoalBeforeBlank);
        levelLeftOnGoalBeforeGoal = new Level(PositionLeftOnGoalBeforeGoal, directionLeftOnGoalBeforeGoal, cellsLeftOnGoalBeforeGoal);
        levelRightOnBlankBeforeWall = new Level(PositionRightOnBlankBeforeWall, directionRightOnBlankBeforeWall, cellsRightOnBlankBeforeWall);

    }

    @AfterEach
    void tearDown() {
        while1 = null;
        while2 = null;
        while3 = null;
        while4 = null;
        while5 = null;
        whileBlock1 = null;
        innerWhileBlock1 = null;
        whileBlock2 = null;
        outerWhileBlock2 = null;

        func1 = null;
        func11 = null;
        func2 = null;
        func1Under = null;
        func5Under = null;

        cond1 = null;
        cond3 = null;
        cond5 = null;

        PositionUpOnBlankBeforeWall = null;
        PositionDownOnGoalBeforeBlank = null;
        PositionLeftOnGoalBeforeGoal = null;
        PositionRightOnBlankBeforeWall = null;

        directionUpOnBlankBeforeWall = null;
        directionDownOnGoalBeforeBlank = null;
        directionLeftOnGoalBeforeGoal = null;
        directionRightOnBlankBeforeWall = null;

        cellsUpOnBlankBeforeWall = null;
        cellsDownOnGoalBeforeBlank = null;
        cellsLeftOnGoalBeforeGoal = null;
        cellsRightOnBlankBeforeWall = null;

        levelUpOnBlankBeforeWall = null;
        levelDownOnGoalBeforeBlank = null;
        levelLeftOnGoalBeforeGoal = null;
        levelRightOnBlankBeforeWall = null;
    }

    @Test
    void getCavitySubConnector() {
        assertEquals(func1.getMainConnector().getConnectedConnector(), while1.getCavitySubConnector());
        assertEquals(func2.getMainConnector().getConnectedConnector(), while2.getCavitySubConnector());
    }

    @Test
    void getConditionalSubConnector() {
        assertEquals(cond1.getMainConnector().getConnectedConnector(), while1.getConditionalSubConnector());
        assertEquals(cond3.getMainConnector().getConnectedConnector(), while3.getConditionalSubConnector());
    }

    @Test
    void getCondition() {
        assertEquals(cond1, while1.getCondition());
        assertNull(while2.getCondition());
        assertEquals(cond3, while3.getCondition());
        assertNull(while4.getCondition());
    }

    @Test
    void hasNext() {
        assertTrue(while1.hasNext());
        assertFalse(while2.hasNext());
        assertFalse(while3.hasNext());
        assertFalse(while4.hasNext());
        assertTrue(while5.hasNext());

        while1.getFunctionality().evaluate(levelUpOnBlankBeforeWall);
        assertTrue(while1.hasNext());

        while2.getFunctionality().evaluate(levelDownOnGoalBeforeBlank);
        assertFalse(while2.hasNext());

        while3.getFunctionality().evaluate(levelRightOnBlankBeforeWall);
        assertFalse(while3.hasNext());

        while4.getFunctionality().evaluate(levelLeftOnGoalBeforeGoal);
        assertFalse(while4.hasNext());

        while5.getFunctionality().evaluate(levelUpOnBlankBeforeWall);
        assertFalse(while5.hasNext());
    }

    @Test
    void getNext() {
        assertEquals(func1Under, while1.getNext());
        assertNull(while2.getNext());
        assertNull(while3.getNext());
        assertNull(while4.getNext());
        assertEquals(func5Under, while5.getNext());

        while1.getFunctionality().evaluate(levelLeftOnGoalBeforeGoal);
        assertEquals(func1Under, while1.getNext());

        while2.getFunctionality().evaluate(levelRightOnBlankBeforeWall);
        assertNull(while2.getNext());

        while3.getFunctionality().evaluate(levelUpOnBlankBeforeWall);
        assertNull(while3.getNext());

        while4.getFunctionality().evaluate(levelLeftOnGoalBeforeGoal);
        assertNull(while4.getNext());

        while5.getFunctionality().evaluate(levelUpOnBlankBeforeWall);
        assertNull(while5.getNext());
    }

    @Test
    void hasProperConnections() {
        assertTrue(while1.hasProperConnections());
        assertFalse(while2.hasProperConnections());
        assertTrue(while3.hasProperConnections());
        assertFalse(while4.hasProperConnections());
        assertTrue(while5.hasProperConnections());
        assertFalse(whileBlock1.hasProperConnections());
        assertFalse(whileBlock2.hasProperConnections());
    }

//    @Test
//    void getNextIfNone() {
//        assertEquals(while1, while1.getNextIfNone());
//        while1.setAlreadyRan(true);
//        assertEquals(func3, while1.getNextIfNone());
//    }

    @Test
    void getNbSubConnectors() {
        assertEquals(3, while1.getNbSubConnectors());
        assertEquals(3, while2.getNbSubConnectors());
        assertEquals(3, while3.getNbSubConnectors());
        assertEquals(3, while4.getNbSubConnectors());
        assertEquals(3, while5.getNbSubConnectors());
    }

//    @Test
//    void hasAlreadyRan() {
//        assertFalse(while1.hasAlreadyRan());
//        assertFalse(while2.hasAlreadyRan());
//        assertFalse(while3.hasAlreadyRan());
//        assertFalse(while4.hasAlreadyRan());
//        assertFalse(while5.hasAlreadyRan());
//        while1.setAlreadyRan(true);
//        while2.setAlreadyRan(true);
//        while3.setAlreadyRan(false);
//        while4.setAlreadyRan(false);
//        while5.setAlreadyRan(true);
//        assertTrue(while1.hasAlreadyRan());
//        assertTrue(while2.hasAlreadyRan());
//        assertFalse(while3.hasAlreadyRan());
//        assertFalse(while4.hasAlreadyRan());
//        assertTrue(while5.hasAlreadyRan());
//    }
//
//    @Test
//    void setAlreadyRan() {
//        assertFalse(while1.hasAlreadyRan());
//        while1.setAlreadyRan(true); // false -> true
//        assertTrue(while1.hasAlreadyRan());
//        while1.setAlreadyRan(true); // true -> true
//        assertTrue(while1.hasAlreadyRan());
//        while1.setAlreadyRan(false); // true -> false
//        assertFalse(while1.hasAlreadyRan());
//        while1.setAlreadyRan(false); // false -> false
//        assertFalse(while1.hasAlreadyRan());
//    }
//
//    @Test
//    void reset() {
//        while1.setAlreadyRan(true);
//        func1.setAlreadyRan(true);
//        func11.setAlreadyRan(true);
//        func1Under.setAlreadyRan(true);
//        cond1.setAlreadyRan(true);
//        while1.reset();
//        assertFalse(while1.hasAlreadyRan());
//        assertFalse(func1.hasAlreadyRan());
//        assertFalse(func11.hasAlreadyRan());
//        assertFalse(func1Under.hasAlreadyRan());
//        assertFalse(cond1.hasAlreadyRan());
//
//        while4.setAlreadyRan(true);
//        while4.reset();
//        assertFalse(while4.hasAlreadyRan());
//    }
}