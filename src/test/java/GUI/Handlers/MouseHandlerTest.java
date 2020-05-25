package GUI.Handlers;

import Controllers.ControllerClasses.BlockHandlerController;
import Controllers.ControllerClasses.ConnectionController;
import Controllers.ControllerClasses.HistoryController;
import Controllers.Utility.IGUISystemBlockLink;
import Controllers.Utility.ProgramEventManager;
import GUI.Blocks.*;
import GUI.Panels.PalettePanel;
import GUI.Panels.ProgramAreaPanel;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.Actions.MoveForwardAction;
import GameWorldUtility.Actions.TurnLeftAction;
import GameWorldUtility.Actions.TurnRightAction;
import GameWorldUtility.LevelInitializer;
import GameWorldUtility.Predicates.WallInFrontPredicate;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.CommandHistory;
import System.Logic.ProgramArea.Handlers.PABlockHandler;
import System.Logic.ProgramArea.ProgramArea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MouseHandlerTest {

    ProgramAreaPanel programAreaPanel;
    PalettePanel palette;
    static final int CORNER_X_PA = 400, CORNER_Y_PA = 0, CORNER_X_PALETTE = 0, CORNER_Y_PALETTE = 0;
    static final int WIDTH = 400, HEIGHT = 800;
    BlockHandlerController blockHandlerController;
    ConnectionController connectionController;
    IGUISystemBlockLink converter;
    PABlockHandler paBlockHandler;
    ProgramArea programArea;
    GameWorldType type;
    GameWorld gameWorld;
    Block moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock;
    CommandHistory history;
    GUIBlock cavity, functional, conditional, operator;
    String cavityName, functionalName, conditionalName, operatorName;
    ProgramEventManager manager;
    GUIBlockHandler guiBlockHandler;
    HistoryController historyController;
    MouseHandler mouseHandler;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        gameWorld = type.createNewGameWorld();
        history = new CommandHistory();
        programArea = new ProgramArea(gameWorld, history);

        moveForward = new FunctionalBlock(new ActionFunctionality((MoveForwardAction) type.getAllActions().get(0)));
        turnLeft = new FunctionalBlock(new ActionFunctionality((TurnLeftAction) type.getAllActions().get(1)));
        turnRight = new FunctionalBlock(new ActionFunctionality((TurnRightAction) type.getAllActions().get(2)));
        wallInFront = new PredicateBlock(new PredicateFunctionality((WallInFrontPredicate) type.getAllPredicates().get(0)));
        notBlock = new NotBlock();
        whileBlock = new WhileBlock();
        ifBlock = new IfBlock();

        paBlockHandler = new PABlockHandler(new ArrayList<>(Arrays.asList(moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock)), programArea);
        converter = new IGUISystemBlockLink();

        cavityName = "Cavity";
        functionalName = "functional";
        conditionalName = "conditional";
        operatorName = "operator";

        cavity = new GUICavityBlock(cavityName, 0,0);
        functional = new GUIFunctionalBlock(functionalName, 0,0);
        conditional = new GUIConditionalBlock(conditionalName, 0,0);
        operator = new GUIOperatorBlock(operatorName, 0,0);

        blockHandlerController = new BlockHandlerController(converter, paBlockHandler);
        connectionController = new ConnectionController(converter, paBlockHandler);

        programAreaPanel = new ProgramAreaPanel(CORNER_X_PA, CORNER_Y_PA, WIDTH, HEIGHT, blockHandlerController, connectionController);
        palette = new PalettePanel(CORNER_X_PALETTE, CORNER_Y_PALETTE, WIDTH, HEIGHT, new ArrayList<>(Arrays.asList(cavity, functional, conditional, operator)));

        manager = new ProgramEventManager();
        manager.subscribe(programAreaPanel);

        historyController = new HistoryController(history, programArea);
        guiBlockHandler = new GUIBlockHandler(palette, programAreaPanel);

        mouseHandler = new MouseHandler(guiBlockHandler, historyController);
    }

    @AfterEach
    void tearDown() {
        manager.unsubscribe(programAreaPanel);
        manager = null;
        programAreaPanel = null;
        blockHandlerController = null;
        connectionController = null;
        converter = null;
        paBlockHandler = null;
        moveForward = null;
        turnLeft = null;
        turnRight = null;
        wallInFront = null;
        notBlock = null;
        whileBlock = null;
        ifBlock = null;
        programArea = null;
        history = null;
        gameWorld = null;
        type = null;
        cavityName = null;
        functionalName = null;
        conditionalName = null;
        operatorName = null;
        cavity = null;
        functional = null;
        conditional = null;
        operator = null;
        palette = null;
        historyController = null;
        guiBlockHandler = null;
    }

    @Test
    void mouseHandler_invalidGUIBlockHandler() {
        assertThrows(IllegalArgumentException.class, () -> new MouseHandler(null, historyController));
    }

    @Test
    void mouseHandler_invalidHistoryController() {
        assertThrows(IllegalArgumentException.class, () -> new MouseHandler(guiBlockHandler, null));
    }

    @Test
    void isValidGUIBlockHandler() {
        assertTrue(MouseHandler.isValidGUIBlockHandler(guiBlockHandler));
        assertFalse(MouseHandler.isValidGUIBlockHandler(null));
    }

    @Test
    void isValidHistoryController() {
        assertTrue(MouseHandler.isValidHistoryController(historyController));
        assertFalse(MouseHandler.isValidHistoryController(null));
    }

    @Test
    void handleMouseEvent_releaseWithoutPressing() {
        assertNull(programArea.getProgram());
        assertFalse(guiBlockHandler.blocksAreDragged());

        mouseHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, cavity.getX(), cavity.getY());
        assertNull(programArea.getProgram());
        assertFalse(guiBlockHandler.blocksAreDragged());
    }

    @Test
    void handleMouseEvent_pressTwice() {
        assertNull(programArea.getProgram());
        assertFalse(guiBlockHandler.blocksAreDragged());

        mouseHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, cavity.getX(), cavity.getY());
        assertNull(programArea.getProgram());
        assertTrue(guiBlockHandler.blocksAreDragged());

        mouseHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, CORNER_X_PA + WIDTH/2, CORNER_Y_PA + HEIGHT/2);

        assertNull(programArea.getProgram());
        assertTrue(guiBlockHandler.blocksAreDragged());
    }

    @Test
    void handleMouseEvent() {
        assertNull(programArea.getProgram());
        assertFalse(guiBlockHandler.blocksAreDragged());

        mouseHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, cavity.getX(), cavity.getY());
        assertNull(programArea.getProgram());
        assertTrue(guiBlockHandler.blocksAreDragged());

        mouseHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, CORNER_X_PA + WIDTH/2, CORNER_Y_PA + HEIGHT/2);

        assertNotNull(programArea.getProgram());
        assertFalse(guiBlockHandler.blocksAreDragged());
    }
}