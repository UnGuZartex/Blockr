//package System.UseCases;
//
//import GameWorld.Cell;
//import GameWorld.CellType;
//import GameWorld.Grid;
//import GameWorld.Level;
//import GameWorldAPI.GameWorld.GameWorld;
//import GameWorldAPI.GameWorldType.GameWorldType;
//import GameWorldUtility.Actions.MoveForwardAction;
//import GameWorldUtility.Actions.TurnLeftAction;
//import GameWorldUtility.Actions.TurnRightAction;
//import GameWorldUtility.LevelInitializer;
//import GameWorldUtility.Predicates.WallInFrontPredicate;
//import RobotCollection.Robot.Direction;
//import RobotCollection.Robot.Robot;
//import RobotCollection.Utility.GridPosition;
//import System.BlockStructure.Blocks.*;
//import System.BlockStructure.Functionality.ActionFunctionality;
//import System.BlockStructure.Functionality.PredicateFunctionality;
//import System.Logic.CommandHistory;
//import System.Logic.ProgramArea.*;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ResetTest {
//
//    ProgramCommand commandRun, commandReset;
//    PABlockHandler paBlockHandler;
//    Robot robot;
//    Grid grid;
//    GameWorldType type;
//    GameWorld gameWorld;
//    Block moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock;
//
//    @BeforeEach
//    void setUp() {
//        type = new LevelInitializer();
//        robot = new Robot(new GridPosition(1,1), Direction.LEFT);
//        grid = new Grid(new Cell[][] {
//                {new Cell(CellType.WALL), new Cell(CellType.WALL), new Cell(CellType.WALL)},
//                {new Cell(CellType.WALL), new Cell(CellType.BLANK), new Cell(CellType.GOAL)},
//                {new Cell(CellType.WALL), new Cell(CellType.WALL), new Cell(CellType.WALL)},
//        });
//        gameWorld = new Level(robot, grid);
//
//        moveForward = new FunctionalBlock(new ActionFunctionality((MoveForwardAction) type.getAllActions().get(0)));
//        turnLeft = new FunctionalBlock(new ActionFunctionality((TurnLeftAction) type.getAllActions().get(1)));
//        turnRight = new FunctionalBlock(new ActionFunctionality((TurnRightAction) type.getAllActions().get(2)));
//        wallInFront = new PredicateBlock(new PredicateFunctionality((WallInFrontPredicate) type.getAllPredicates().get(0)));
//        notBlock = new NotBlock();
//        whileBlock = new WhileBlock();
//        ifBlock = new IfBlock();
//
//        paBlockHandler = new PABlockHandler(new ArrayList<>(Arrays.asList(moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock)), new ProgramArea(gameWorld, new CommandHistory()));
//
//        commandRun = new RunProgramCommand(paBlockHandler.getPA());
//        commandReset = new ResetProgramCommand(paBlockHandler.getPA());
//    }
//
//    @AfterEach
//    void tearDown() {
//        type = null;
//        robot = null;
//        grid = null;
//        gameWorld = null;
//        moveForward = null;
//        turnLeft = null;
//        turnRight = null;
//        wallInFront = null;
//        notBlock = null;
//        whileBlock = null;
//        ifBlock = null;
//        commandRun = null;
//        commandReset = null;
//    }
//
//    void checkIfReset() {
//        assertNotEquals(robot, ((Level)gameWorld).getRobot());
//        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
//        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
//        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
//    }
//
//    boolean isChanged() {
//        return 1 != ((Level)gameWorld).getRobot().getGridPosition().getX() ||
//               1 != ((Level)gameWorld).getRobot().getGridPosition().getX() ||
//                !Direction.LEFT.name().equals(((Level) gameWorld).getRobot().getDirection());
//    }
//
//    @Test
//    void gameWorldChanged() {
//        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
//        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
//        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
//        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);
//
//        paBlockHandler.addToPA(whileBlock);
//        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
//        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
//        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));
//
//        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
//        assertTrue(paBlockHandler.hasProperNbBlocks());
//        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
//
//        commandRun.execute();
//        commandRun.execute();
//        commandRun.execute();
//        commandRun.execute();
//        commandRun.execute();
//        commandRun.execute();
//        assertTrue(this::isChanged);
//        commandReset.execute();
//        checkIfReset();
//    }
//
//    @Test
//    void gameWorldNotChanged() {
//        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
//        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
//        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
//        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);
//
//        paBlockHandler.addToPA(whileBlock);
//        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
//        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
//        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));
//
//        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
//        assertTrue(paBlockHandler.hasProperNbBlocks());
//        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
//
//        assertFalse(this::isChanged);
//        commandReset.execute();
//        checkIfReset();
//    }
//
//    @Test
//    void multiplePrograms() {
//        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
//        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);
//
//        paBlockHandler.addToPA(turnRight);
//        paBlockHandler.addToPA(moveForward);
//
//        assertEquals(2, paBlockHandler.getPA().getAllBlocksCount());
//        assertTrue(paBlockHandler.hasProperNbBlocks());
//        assertNull(paBlockHandler.getPA().getProgram());
//
//        assertThrows(IllegalStateException.class, () -> commandReset.execute());
//    }
//
//    @Test
//    void noPrograms() {
//        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
//        assertTrue(paBlockHandler.hasProperNbBlocks());
//        assertNull(paBlockHandler.getPA().getProgram());
//
//        assertThrows(IllegalStateException.class, () -> commandReset.execute());
//    }
//}