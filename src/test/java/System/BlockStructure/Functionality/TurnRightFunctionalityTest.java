package System.BlockStructure.Functionality;

import System.GameWorld.Cell;
import System.GameWorld.CellType;
import System.GameWorld.Direction;
import System.GameWorld.Level.Level;
import Utility.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TurnRightFunctionalityTest {

    private TurnRightFunctionality turnRight;

    private Level levelUp, levelDown, levelLeft, levelRight;
    private Cell[][] cellsUp, cellsDown, cellsLeft, cellsRight;
    private int xUp, yUp, xDown, yDown, xLeft, yLeft, xRight, yRight;
    private static final int MAX_WIDTH = 20;
    private static final int MAX_HEIGHT = 20;
    private Random random;

    @BeforeEach
    void setUp() {
        turnRight = new TurnRightFunctionality();

        random = new Random();
        int heightUp, heightDown, heightLeft, heightRight;
        int widthUp, widthDown, widthLeft, widthRight;
        widthUp = random.nextInt(MAX_WIDTH) + 1;
        heightUp = random.nextInt(MAX_HEIGHT) + 1;
        widthDown = random.nextInt(MAX_WIDTH) + 1;
        heightDown = random.nextInt(MAX_HEIGHT) + 1;
        widthLeft = random.nextInt(MAX_WIDTH) + 1;
        heightLeft = random.nextInt(MAX_HEIGHT) + 1;
        widthRight = random.nextInt(MAX_WIDTH) + 1;
        heightRight = random.nextInt(MAX_HEIGHT) + 1;
        xUp = random.nextInt(widthUp);
        yUp = random.nextInt(heightUp);
        xDown = random.nextInt(widthDown);
        yDown = random.nextInt(heightDown);
        xLeft = random.nextInt(widthLeft);
        yLeft = random.nextInt(heightLeft);
        xRight = random.nextInt(widthRight) ;
        yRight = random.nextInt(heightRight);

        CellType[] cellTypes = CellType.values();
        cellsUp = new Cell[widthUp][heightUp];
        for (int x = 0; x < widthUp; x++) {
            for (int y = 0; y < heightUp; y++) {
                cellsUp[x][y] = new Cell(cellTypes[random.nextInt(cellTypes.length)]);
            }
        }
        cellsUp[xUp][yUp] = new Cell(CellType.BLANK);
        cellsDown = new Cell[widthDown][heightDown];
        for (int x = 0; x < widthDown; x++) {
            for (int y = 0; y < heightDown; y++) {
                cellsDown[x][y] = new Cell(cellTypes[random.nextInt(cellTypes.length)]);
            }
        }
        cellsDown[xDown][yDown] = new Cell(CellType.BLANK);
        cellsLeft = new Cell[widthLeft][heightLeft];
        for (int x = 0; x < widthLeft; x++) {
            for (int y = 0; y < heightLeft; y++) {
                cellsLeft[x][y] = new Cell(cellTypes[random.nextInt(cellTypes.length)]);
            }
        }
        cellsLeft[xLeft][yLeft] = new Cell(CellType.BLANK);
        cellsRight = new Cell[widthRight][heightRight];
        for (int x = 0; x < widthRight; x++) {
            for (int y = 0; y < heightRight; y++) {
                cellsRight[x][y] = new Cell(cellTypes[random.nextInt(cellTypes.length)]);
            }
        }
        cellsRight[xRight][yRight] = new Cell(CellType.BLANK);

        levelUp = new Level(new Position(xUp, yUp), Direction.UP, cellsUp);
        levelDown = new Level(new Position(xDown, yDown), Direction.DOWN, cellsDown);
        levelLeft = new Level(new Position(xLeft, yLeft), Direction.LEFT, cellsLeft);
        levelRight = new Level(new Position(xRight, yRight), Direction.RIGHT, cellsRight);
    }

    @AfterEach
    void tearDown() {
        random = null;

        turnRight = null;

        cellsUp = null;
        cellsDown = null;
        cellsLeft = null;
        cellsRight = null;

        levelUp = null;
        levelDown = null;
        levelLeft = null;
        levelRight = null;
    }

    @Test
    void getEvaluation() {
        assertFalse(turnRight.getEvaluation());
    }

    @Test
    void evaluate() {
        turnRight.evaluate(levelUp);
        assertEquals(xUp, levelUp.getRobot().getX());
        assertEquals(yUp, levelUp.getRobot().getY());
        assertEquals(Direction.RIGHT, levelUp.getRobot().getDirection());

        turnRight.evaluate(levelDown);
        assertEquals(xDown, levelDown.getRobot().getX());
        assertEquals(yDown, levelDown.getRobot().getY());
        assertEquals(Direction.LEFT, levelDown.getRobot().getDirection());

        turnRight.evaluate(levelLeft);
        assertEquals(xLeft, levelLeft.getRobot().getX());
        assertEquals(yLeft, levelLeft.getRobot().getY());
        assertEquals(Direction.UP, levelLeft.getRobot().getDirection());

        turnRight.evaluate(levelRight);
        assertEquals(xRight, levelRight.getRobot().getX());
        assertEquals(yRight, levelRight.getRobot().getY());
        assertEquals(Direction.DOWN, levelRight.getRobot().getDirection());
    }
}