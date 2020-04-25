//package System.BlockStructure.Functionality;
//
//import GameWorldAPI.GameWorld.GameWorld;
//import GameWorldAPI.GameWorld.Result;
//import GameWorldAPI.GameWorldType.Action;
//import GameWorldAPI.GameWorldType.GameWorldType;
//import GameWorldUtility.Actions.TurnLeftAction;
//import GameWorldUtility.LevelInitializer;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ActionFunctionalityTest {
//
//    Action action;
//    BlockFunctionality functionality;
//    GameWorldType type;
//    GameWorld gameWorld;
//
//    @BeforeEach
//    void setUp() {
//        type = new LevelInitializer();
//        gameWorld = type.createNewGameWorld();
//        action = new TurnLeftAction();
//        functionality = new ActionFunctionality(action, gameWorld);
//    }
//
//    @AfterEach
//    void tearDown() {
//        type = null;
//        gameWorld = null;
//        action = null;
//        functionality = null;
//    }
//
//    @Test
//    void evaluate() {
//        assertEquals(Result.SUCCESS, functionality.evaluate());
//    }
//
//    @Test
//    void copy() {
//        BlockFunctionality func = functionality.copy();
//        assertNotEquals(func, functionality);
//        assertTrue(func instanceof ActionFunctionality);
//        assertEquals(func.getGameWorld(), functionality.getGameWorld());
//    }
//}