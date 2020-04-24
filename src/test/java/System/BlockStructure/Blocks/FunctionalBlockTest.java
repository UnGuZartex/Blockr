//package System.BlockStructure.Blocks;
//
//
//import GameWorld.Level;
//import GameWorldUtility.Actions.MoveForwardAction;
//import GameWorldUtility.Actions.TurnLeftAction;
//import GameWorldUtility.Actions.TurnRightAction;
//import GameWorldUtility.LevelInitializer;
//import System.BlockStructure.Functionality.ActionFunctionality;
//import System.Logic.ProgramArea.ConnectionHandler;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class FunctionalBlockTest {
//
//    FunctionalBlock turnRight, turnLeft, moveForward;
//
//    @BeforeEach
//    void setUp() {
//        LevelInitializer init = new LevelInitializer();
//        Level level = (Level) init.createNewGameWorld();
//        turnLeft = new FunctionalBlock(new ActionFunctionality(new TurnLeftAction()));
//        turnRight = new FunctionalBlock(new ActionFunctionality(new TurnRightAction()));
//        moveForward = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction()));
//
//        ConnectionHandler handler = new ConnectionHandler();
//        handler.connect(turnRight, turnLeft.getSubConnectorAt(0));
//    }
//
//    @AfterEach
//    void tearDown() {
//        turnRight = null;
//        turnLeft = null;
//        moveForward = null;
//    }
//
//    @Test
//    void getNbSubConnectors() {
//        assertEquals(1, turnLeft.getNbSubConnectors());
//        assertEquals(1, turnRight.getNbSubConnectors());
//        assertEquals(1, moveForward.getNbSubConnectors());
//    }
//
//    @Test
//    void hasProperConnections() {
//        assertTrue(turnRight.hasProperConnections());
//        assertTrue(turnLeft.hasProperConnections());
//        assertTrue(moveForward.hasProperConnections());
//    }
//
//    @Test
//    void hasNext() {
//        assertFalse(turnRight.hasNext());
//        assertTrue(turnLeft.hasNext());
//        assertFalse(moveForward.hasNext());
//    }
//
//    @Test
//    void getNext() {
//        assertNull(turnRight.getNext());
//        assertNull(moveForward.getNext());
//        assertEquals(turnLeft.getNext(), turnRight);
//    }
//
//    @Test
//    void cloneTest() {
//        Block block = turnLeft.clone();
//        assertNotEquals(block, turnLeft);
//        assertEquals(block.getFunctionality(), turnLeft.getFunctionality());
//        assertEquals(block.getFunctionality().getGameWorld(), turnLeft.getFunctionality().getGameWorld());
//        assertTrue(block instanceof FunctionalBlock);
//        assertTrue(block.getFunctionality() instanceof ActionFunctionality);
//        assertFalse(block.getSubConnectorAt(0).isConnected());
//        assertFalse(block.getMainConnector().isConnected());
//    }
//}