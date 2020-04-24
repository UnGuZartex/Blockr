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
//import System.BlockStructure.Functionality.CavityFunctionality;
//import System.BlockStructure.Functionality.NotFunctionality;
//import System.BlockStructure.Functionality.PredicateFunctionality;
//import System.Logic.ProgramArea.PABlockHandler;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//public class ResetTest {
//
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
//        moveForward = new FunctionalBlock(new ActionFunctionality((MoveForwardAction) type.getAllActions().get(0), gameWorld));
//        turnLeft = new FunctionalBlock(new ActionFunctionality((TurnLeftAction) type.getAllActions().get(1), gameWorld));
//        turnRight = new FunctionalBlock(new ActionFunctionality((TurnRightAction) type.getAllActions().get(2), gameWorld));
//        wallInFront = new StatementBlock(new PredicateFunctionality((WallInFrontPredicate) type.getAllPredicates().get(0), gameWorld));
//        notBlock = new NotBlock(new NotFunctionality(gameWorld));
//        whileBlock = new WhileBlock(new CavityFunctionality(gameWorld));
//        ifBlock = new IfBlock(new CavityFunctionality(gameWorld));
//
//        paBlockHandler = new PABlockHandler(new ArrayList<>(Arrays.asList(moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock)));
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
//    }
//
//    @Test
//    void test() {
//        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
//        StatementBlock wallInFront = (StatementBlock) paBlockHandler.getFromPalette(3);
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
//
//        paBlockHandler.getPA().runProgramStep();
//        paBlockHandler.getPA().runProgramStep();
//        paBlockHandler.getPA().runProgramStep();
//        paBlockHandler.getPA().runProgramStep();
//        paBlockHandler.getPA().runProgramStep();
//        paBlockHandler.getPA().resetProgram();
//
//        // TODO checks for reset with history
//    }
//}