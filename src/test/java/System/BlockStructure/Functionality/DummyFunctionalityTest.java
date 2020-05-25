package System.BlockStructure.Functionality;

import GameWorld.Level;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DummyFunctionalityTest {

    GameWorldType type;
    GameWorld gameWorld;
    BlockFunctionality functionality;
    String direction;
    int x, y;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        gameWorld = type.createNewGameWorld();
        functionality = new DummyFunctionality();
        direction = ((Level)gameWorld).getRobot().getDirection();
        x = ((Level)gameWorld).getRobot().getGridPosition().getX();
        y = ((Level)gameWorld).getRobot().getGridPosition().getY();
    }

    @AfterEach
    void tearDown() {
        type = null;
        gameWorld = null;
        functionality = null;
        direction = null;
    }

    @Test
    void evaluate() {
        assertEquals(direction, ((Level)gameWorld).getRobot().getDirection());
        assertEquals(x, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(y, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Result.SUCCESS, functionality.evaluate(gameWorld));
        assertEquals(direction, ((Level)gameWorld).getRobot().getDirection());
        assertEquals(x, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(y, ((Level)gameWorld).getRobot().getGridPosition().getY());
    }
}