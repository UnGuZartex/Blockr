package System.BlockStructure.Blocks;

import GameWorld.Cell;
import GameWorld.CellType;
import GameWorld.Grid;
import GameWorld.Level;
import GameWorldUtility.Actions.MoveForwardAction;
import GameWorldUtility.Predicates.WallInFrontPredicate;
import RobotCollection.Robot.Direction;
import RobotCollection.Robot.Robot;
import RobotCollection.Utility.GridPosition;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.CavityFunctionality;
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.ProgramArea.ConnectionHandler;
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
    private GridPosition PositionUpOnBlankBeforeWall, PositionDownOnGoalBeforeBlank,
            PositionLeftOnGoalBeforeGoal, PositionRightOnBlankBeforeWall;
    private Direction directionUpOnBlankBeforeWall, directionDownOnGoalBeforeBlank,
            directionLeftOnGoalBeforeGoal, directionRightOnBlankBeforeWall;
    private Cell[][] cellsUpOnBlankBeforeWall, cellsDownOnGoalBeforeBlank,
            cellsLeftOnGoalBeforeGoal, cellsRightOnBlankBeforeWall;

    @BeforeEach
    void setUp() {


        PositionUpOnBlankBeforeWall = new GridPosition(1,1);
        PositionDownOnGoalBeforeBlank = new GridPosition(1,1);
        PositionLeftOnGoalBeforeGoal = new GridPosition(1,1);
        PositionRightOnBlankBeforeWall = new GridPosition(1,1);

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
        Robot upRobot = new Robot(PositionUpOnBlankBeforeWall, directionUpOnBlankBeforeWall);
        levelUpOnBlankBeforeWall = new Level(upRobot, new Grid(cellsUpOnBlankBeforeWall));

        Robot downRobot = new Robot(PositionDownOnGoalBeforeBlank, directionDownOnGoalBeforeBlank);
        levelDownOnGoalBeforeBlank = new Level(downRobot, new Grid(cellsDownOnGoalBeforeBlank));

        Robot leftRobot = new Robot(PositionLeftOnGoalBeforeGoal, directionLeftOnGoalBeforeGoal);
        levelLeftOnGoalBeforeGoal = new Level(leftRobot, new Grid(cellsLeftOnGoalBeforeGoal));

        Robot rightRobot = new Robot(PositionRightOnBlankBeforeWall, directionRightOnBlankBeforeWall);
        levelRightOnBlankBeforeWall = new Level(rightRobot, new Grid(cellsRightOnBlankBeforeWall));

        while1 = new WhileBlock(new CavityFunctionality(levelUpOnBlankBeforeWall));
        while2 = new WhileBlock(new CavityFunctionality(levelDownOnGoalBeforeBlank));
        while3 = new WhileBlock(new CavityFunctionality(levelRightOnBlankBeforeWall));
        while4 = new WhileBlock(new CavityFunctionality(levelLeftOnGoalBeforeGoal));
        while5 = new WhileBlock(new CavityFunctionality(levelUpOnBlankBeforeWall));
        whileBlock1 = new WhileBlock(new CavityFunctionality(levelUpOnBlankBeforeWall));
        innerWhileBlock1 = new WhileBlock(new CavityFunctionality(levelUpOnBlankBeforeWall));
        whileBlock2 =new WhileBlock(new CavityFunctionality(levelUpOnBlankBeforeWall));
        outerWhileBlock2 = new WhileBlock(new CavityFunctionality(levelUpOnBlankBeforeWall));

        func1 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),levelUpOnBlankBeforeWall));
        func11 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),levelUpOnBlankBeforeWall));
        func2 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),levelDownOnGoalBeforeBlank));
        func3 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),levelRightOnBlankBeforeWall));
        func1Under = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),levelUpOnBlankBeforeWall));
        func5Under = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),levelUpOnBlankBeforeWall));

        cond1 = new StatementBlock(new PredicateFunctionality(new WallInFrontPredicate(),levelUpOnBlankBeforeWall));
        cond3 = new StatementBlock(new PredicateFunctionality(new WallInFrontPredicate(),levelRightOnBlankBeforeWall));
        cond5 = new StatementBlock(new PredicateFunctionality(new WallInFrontPredicate(),levelUpOnBlankBeforeWall));
        condBlock1 = new StatementBlock(new PredicateFunctionality(new WallInFrontPredicate(),levelUpOnBlankBeforeWall));
        condBlock2 = new StatementBlock(new PredicateFunctionality(new WallInFrontPredicate(),levelUpOnBlankBeforeWall));

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

        while1.getFunctionality().evaluate();
        assertTrue(while1.hasNext());

        while2.getFunctionality().evaluate();
        assertFalse(while2.hasNext());

        while3.getFunctionality().evaluate();
        assertFalse(while3.hasNext());

        while4.getFunctionality().evaluate();
        assertFalse(while4.hasNext());

        while5.getFunctionality().evaluate();
        assertFalse(while5.hasNext());
    }

    @Test
    void getNext() {
        assertEquals(func1Under, while1.getNext());
        assertNull(while2.getNext());
        assertNull(while3.getNext());
        assertNull(while4.getNext());
        assertEquals(func5Under, while5.getNext());

        while1.getFunctionality().evaluate();
        assertEquals(func1, while1.getNext());

        while2.getFunctionality().evaluate();
        assertNull(while2.getNext());

        while3.getFunctionality().evaluate();
        assertEquals(while3, while3.getNext());

        while4.getFunctionality().evaluate();
        assertNull(while4.getNext());

        while5.getFunctionality().evaluate();
        assertEquals(while5, while5.getNext());
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
    

    @Test
    void getNbSubConnectors() {
        assertEquals(3, while1.getNbSubConnectors());
        assertEquals(3, while2.getNbSubConnectors());
        assertEquals(3, while3.getNbSubConnectors());
        assertEquals(3, while4.getNbSubConnectors());
        assertEquals(3, while5.getNbSubConnectors());
    }

    @Test
    void getNewReturnBlock() {
        assertEquals(while1, while1.getNewReturnBlock());
        assertEquals(while2, while2.getNewReturnBlock());
        assertEquals(while3, while3.getNewReturnBlock());
        assertEquals(while4, while4.getNewReturnBlock());
        assertEquals(while5, while5.getNewReturnBlock());
    }

    @Test
    void cloneTest() {
        Block block = whileBlock1.clone();
        assertNotEquals(block, whileBlock1);
        assertNotEquals(block.getFunctionality(), whileBlock1.getFunctionality());
        assertEquals(block.getFunctionality().getGameWorld(), whileBlock1.getFunctionality().getGameWorld());
        assertTrue(block instanceof WhileBlock);
        assertTrue(block.getFunctionality() instanceof CavityFunctionality);
        assertFalse(block.getSubConnectorAt(0).isConnected());
        assertFalse(block.getSubConnectorAt(1).isConnected());
        assertFalse(block.getSubConnectorAt(2).isConnected());
        assertFalse(block.getMainConnector().isConnected());
    }
}