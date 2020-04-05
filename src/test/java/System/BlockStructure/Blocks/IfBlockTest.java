package System.BlockStructure.Blocks;

import GameWorld.Cell;
import GameWorld.CellType;
import GameWorld.Level;
import GameWorldUtility.MoveForwardAction;
import GameWorldUtility.WallInFrontPredicate;
import RobotCollection.Robot.Robot;
import RobotCollection.Utility.Direction;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.ProgramArea.ConnectionHandler;
import Utility.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfBlockTest {

    IfBlock if1, if2, if3, if4, if5;
    FunctionalBlock func1, func11, func2, func3, func1Under, func5Under;
    ConditionalBlock cond1, cond3, cond5;

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
        if1 = new IfBlock();
        if2 = new IfBlock();
        if3 = new IfBlock();
        if4 = new IfBlock();
        if5 = new IfBlock();

        func1 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction()));
        func11 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction()));
        func2 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction()));
        func3 =new FunctionalBlock(new ActionFunctionality(new MoveForwardAction()));
        func1Under = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction()));
        func5Under = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction()));

        cond1 = new StatementBlock(new PredicateFunctionality(new WallInFrontPredicate()));
        cond3 = new StatementBlock(new PredicateFunctionality(new WallInFrontPredicate()));
        cond5 = new StatementBlock(new PredicateFunctionality(new WallInFrontPredicate()));

        ConnectionHandler handler = new ConnectionHandler();
        handler.connect(if1, func3.getSubConnectorAt(0));
        handler.connect(func11, func1.getSubConnectorAt(0));
        handler.connect(func1, if1.getCavitySubConnector());
        handler.connect(cond1, if1.getConditionalSubConnector());
        handler.connect(func1Under, if1.getSubConnectorAt(0));
        handler.connect(func2, if2.getCavitySubConnector());
        handler.connect(cond3, if3.getConditionalSubConnector());
        handler.connect(cond5, if5.getConditionalSubConnector());
        handler.connect(func5Under, if5.getSubConnectorAt(0));

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
        Robot upRobot = new Robot(PositionUpOnBlankBeforeWall.getX(), PositionUpOnBlankBeforeWall.getY(), directionUpOnBlankBeforeWall);
        levelUpOnBlankBeforeWall = new Level(upRobot, cellsUpOnBlankBeforeWall);

        Robot downRobot = new Robot(PositionDownOnGoalBeforeBlank.getX(), PositionDownOnGoalBeforeBlank.getY(), directionDownOnGoalBeforeBlank);
        levelDownOnGoalBeforeBlank = new Level(downRobot, cellsDownOnGoalBeforeBlank);

        Robot leftRobot = new Robot(PositionLeftOnGoalBeforeGoal.getX(), PositionLeftOnGoalBeforeGoal.getY(), directionLeftOnGoalBeforeGoal);
        levelLeftOnGoalBeforeGoal = new Level(leftRobot, cellsLeftOnGoalBeforeGoal);

        Robot rightRobot = new Robot(PositionRightOnBlankBeforeWall.getX(), PositionRightOnBlankBeforeWall.getY(), directionRightOnBlankBeforeWall);
        levelRightOnBlankBeforeWall = new Level(rightRobot, cellsRightOnBlankBeforeWall);

    }

    @AfterEach
    void tearDown() {
        if1 = null;
        if1 = null;
        if1 = null;
        if1 = null;
        if1 = null;

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
        assertEquals(func1.getMainConnector().getConnectedConnector(), if1.getCavitySubConnector());
        assertEquals(func2.getMainConnector().getConnectedConnector(), if2.getCavitySubConnector());
    }

    @Test
    void getConditionalSubConnector() {
        assertEquals(cond1.getMainConnector().getConnectedConnector(), if1.getConditionalSubConnector());
        assertEquals(cond3.getMainConnector().getConnectedConnector(), if3.getConditionalSubConnector());

    }

    @Test
    void getCondition() {
        assertEquals(cond1, if1.getCondition());
        assertNull(if2.getCondition());
        assertEquals(cond3, if3.getCondition());
        assertNull(if4.getCondition());
    }

    @Test
    void hasNext() {
        assertTrue(if1.hasNext());
        assertFalse(if2.hasNext());
        assertFalse(if3.hasNext());
        assertFalse(if4.hasNext());
        assertTrue(if5.hasNext());

        if1.getFunctionality().evaluate(levelUpOnBlankBeforeWall);
        assertTrue(if1.hasNext());

        if2.getFunctionality().evaluate(levelDownOnGoalBeforeBlank);
        assertFalse(if2.hasNext());

        if3.getFunctionality().evaluate(levelRightOnBlankBeforeWall);
        assertFalse(if3.hasNext());

        if4.getFunctionality().evaluate(levelLeftOnGoalBeforeGoal);
        assertFalse(if4.hasNext());

        if5.getFunctionality().evaluate(levelUpOnBlankBeforeWall);
        assertFalse(if5.hasNext());
    }

    @Test
    void getNext() {
        assertEquals(func1Under, if1.getNext());
        assertNull(if2.getNext());
        assertNull(if3.getNext());
        assertNull(if4.getNext());
        assertEquals(func5Under, if5.getNext());

        if1.getFunctionality().evaluate(levelLeftOnGoalBeforeGoal);
        assertEquals(func1Under, if1.getNext());

        if2.getFunctionality().evaluate(levelRightOnBlankBeforeWall);
        assertNull(if2.getNext());

        if3.getFunctionality().evaluate(levelUpOnBlankBeforeWall);
        assertNull(if3.getNext());

        if4.getFunctionality().evaluate(levelLeftOnGoalBeforeGoal);
        assertNull(if4.getNext());

        if5.getFunctionality().evaluate(levelUpOnBlankBeforeWall);
        assertEquals(func5Under, if5.getNext());
    }

    @Test
    void hasProperConnections() {
        assertTrue(if1.hasProperConnections());
        assertFalse(if2.hasProperConnections());
        assertTrue(if3.hasProperConnections());
        assertFalse(if4.hasProperConnections());
        assertTrue(if5.hasProperConnections());
    }

//    @Test
//    void getNextIfNone() {
//        assertEquals(func1Under, if1.getNextIfNone());
//        if1.setAlreadyRan(true);
//        assertEquals(func3, if1.getNextIfNone());
//    }

    @Test
    void getNbSubConnectors() {
        assertEquals(3, if1.getNbSubConnectors());
        assertEquals(3, if2.getNbSubConnectors());
        assertEquals(3, if3.getNbSubConnectors());
        assertEquals(3, if4.getNbSubConnectors());
        assertEquals(3, if5.getNbSubConnectors());
    }

//    @Test
//    void hasAlreadyRan() {
//        assertFalse(if1.hasAlreadyRan());
//        assertFalse(if2.hasAlreadyRan());
//        assertFalse(if3.hasAlreadyRan());
//        assertFalse(if4.hasAlreadyRan());
//        assertFalse(if5.hasAlreadyRan());
//        if1.setAlreadyRan(true);
//        if2.setAlreadyRan(true);
//        if3.setAlreadyRan(false);
//        if4.setAlreadyRan(false);
//        if5.setAlreadyRan(true);
//        assertTrue(if1.hasAlreadyRan());
//        assertTrue(if2.hasAlreadyRan());
//        assertFalse(if3.hasAlreadyRan());
//        assertFalse(if4.hasAlreadyRan());
//        assertTrue(if5.hasAlreadyRan());
//    }

//    @Test
//    void setAlreadyRan() {
//        assertFalse(if1.hasAlreadyRan());
//        if1.setAlreadyRan(true); // false -> true
//        assertTrue(if1.hasAlreadyRan());
//        if1.setAlreadyRan(true); // true -> true
//        assertTrue(if1.hasAlreadyRan());
//        if1.setAlreadyRan(false); // true -> false
//        assertFalse(if1.hasAlreadyRan());
//        if1.setAlreadyRan(false); // false -> false
//        assertFalse(if1.hasAlreadyRan());
//    }
//
//    @Test
//    void reset() {
//        if1.setAlreadyRan(true);
//        func1.setAlreadyRan(true);
//        func11.setAlreadyRan(true);
//        func1Under.setAlreadyRan(true);
//        cond1.setAlreadyRan(true);
//        if1.reset();
//        assertFalse(if1.hasAlreadyRan());
//        assertFalse(func1.hasAlreadyRan());
//        assertFalse(func11.hasAlreadyRan());
//        assertFalse(func1Under.hasAlreadyRan());
//        assertFalse(cond1.hasAlreadyRan());
//
//        if4.setAlreadyRan(true);
//        if4.reset();
//        assertFalse(if4.hasAlreadyRan());
//    }
}