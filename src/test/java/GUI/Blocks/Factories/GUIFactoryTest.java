package GUI.Blocks.Factories;

import GUI.Blocks.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GUIFactoryTest {

    GUIBlock block;
    GUIFactory ifFactory, whileFactory, notFactory, mfFactory, tlFactory, trFactory, wifFactory;
    String ifID, whileID, notID, mfID, tlID, trID, wifID;
    int xIf, xWhile, xNot, xMf, xTl, xTr, xWif;
    int yIf, yWhile, yNot, yMf, yTl, yTr, yWif;
    final static int MAX_X = 10, MAX_Y = 10;
    Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
        xIf = random.nextInt(MAX_X) + 1;
        xWhile = random.nextInt(MAX_X) + 1;
        xNot = random.nextInt(MAX_X) + 1;
        xMf = random.nextInt(MAX_X) + 1;
        xTl = random.nextInt(MAX_X) + 1;
        xTr = random.nextInt(MAX_X) + 1;
        xWif = random.nextInt(MAX_X) + 1;
        yIf = random.nextInt(MAX_Y) + 1;
        yWhile = random.nextInt(MAX_Y) + 1;
        yNot = random.nextInt(MAX_Y) + 1;
        yMf = random.nextInt(MAX_Y) + 1;
        yTl = random.nextInt(MAX_Y) + 1;
        yTr = random.nextInt(MAX_Y) + 1;
        yWif = random.nextInt(MAX_Y) + 1;

        ifFactory = new IfGUIFactory();
        whileFactory = new WhileGUIFactory();
        notFactory = new NotGUIFactory();
        mfFactory = new MoveForwardGUIFactory();
        tlFactory = new TurnLeftGUIFactory();
        trFactory = new TurnRightGUIFactory();
        wifFactory = new WallInFrontGUIFactory();

        ifID = "ifID";
        whileID = "whileID";
        notID = "notID";
        mfID = "mfID";
        tlID = "tlID";
        trID = "trID";
        wifID = "wifID";
    }

    @AfterEach
    void tearDown() {
        random = null;

        ifFactory = null;
        whileFactory = null;
        notFactory = null;
        mfFactory = null;
        tlFactory = null;
        trFactory = null;
        wifFactory = null;

        ifID = null;
        whileID = null;
        notID = null;
        mfID = null;
        tlID = null;
        trID = null;
        wifID = null;
    }

    @Test
    void createBlock_If() {
        block = ifFactory.createBlock(ifID, xIf, yIf);
        assertTrue(block instanceof  GUICavityBlock);
        assertEquals(ifID, block.getId());
        assertEquals(xIf, block.getX());
        assertEquals(yIf, block.getY());
    }

    @Test
    void createBlock_While() {
        block = whileFactory.createBlock(whileID, xWhile, yWhile);
        assertTrue(block instanceof  GUICavityBlock);
        assertEquals(whileID, block.getId());
        assertEquals(xWhile, block.getX());
        assertEquals(yWhile, block.getY());
    }

    @Test
    void createBlock_Not() {
        block = notFactory.createBlock(notID, xNot, yNot);
        assertTrue(block instanceof  GUIOperatorBlock);
        assertEquals(notID, block.getId());
        assertEquals(xNot, block.getX());
        assertEquals(yNot, block.getY());
    }

    @Test
    void createBlock_MoveForward() {
        block = mfFactory.createBlock(mfID, xMf, yMf);
        assertTrue(block instanceof  GUIFunctionalityBlock);
        assertEquals(mfID, block.getId());
        assertEquals(xMf, block.getX());
        assertEquals(yMf, block.getY());
    }

    @Test
    void createBlock_TurnLeft() {
        block = tlFactory.createBlock(tlID, xTl, yTl);
        assertTrue(block instanceof  GUIFunctionalityBlock);
        assertEquals(tlID, block.getId());
        assertEquals(xTl, block.getX());
        assertEquals(yTl, block.getY());
    }
    
    @Test
    void createBlock_TurnRight() {
        block = trFactory.createBlock(trID, xTr, yTr);
        assertTrue(block instanceof  GUIFunctionalityBlock);
        assertEquals(trID, block.getId());
        assertEquals(xTr, block.getX());
        assertEquals(yTr, block.getY());
    }

    @Test
    void createBlock_WallInFront() {
        block = wifFactory.createBlock(wifID, xWif, yWif);
        assertTrue(block instanceof  GUIConditionalBlock);
        assertEquals(wifID, block.getId());
        assertEquals(xWif, block.getX());
        assertEquals(yWif, block.getY());
    }
}