//package GUI.Components;
//
//import Controllers.IGUI_System_BlockLink;
//import Controllers.ControllerClasses.BlockHandlerController;
//import Controllers.ControllerClasses.ConnectionController;
//import Controllers.ControllerClasses.HistoryController;
//import Controllers.ProgramEventManager;
//import GUI.Blocks.*;
//import GUI.Panel.PalettePanel;
//import GUI.Panel.ProgramAreaPanel;
//import GameWorldAPI.GameWorld.GameWorld;
//import GameWorldAPI.GameWorldType.GameWorldType;
//import GameWorldAPI.Utility.Snapshot;
//import GameWorldUtility.Actions.MoveForwardAction;
//import GameWorldUtility.Actions.TurnLeftAction;
//import GameWorldUtility.Actions.TurnRightAction;
//import GameWorldUtility.LevelInitializer;
//import GameWorldUtility.Predicates.WallInFrontPredicate;
//import System.BlockStructure.Blocks.*;
//import System.BlockStructure.Functionality.ActionFunctionality;
//import System.BlockStructure.Functionality.PredicateFunctionality;
//import System.Logic.CommandHistory;
//import System.Logic.ProgramArea.PABlockHandler;
//import System.Logic.ProgramArea.ProgramArea;
//import Utility.Position;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.awt.event.MouseEvent;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class GUIBlockHandlerTest {
//
//    ProgramAreaPanel programAreaPanel;
//    PalettePanel palette;
//    static final int CORNER_X_PA = 400, CORNER_Y_PA = 0, CORNER_X_PALETTE = 0, CORNER_Y_PALETTE = 0;
//    static final int WIDTH = 400, HEIGHT = 800;
//    BlockHandlerController blockHandlerController;
//    ConnectionController connectionController;
//    IGUI_System_BlockLink converter;
//    PABlockHandler paBlockHandler;
//    ProgramArea programArea;
//    GameWorldType type;
//    GameWorld gameWorld;
//    Block moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock;
//    CommandHistory history;
//    GUIBlock cavity, functional, conditional, operator;
//    String cavityName, functionalName, conditionalName, operatorName;
//    ProgramEventManager manager;
//    GUIBlock block;
//    GUIBlockHandler guiBlockHandler;
//    HistoryController historyController;
//    Position start, end;
//
//    @BeforeEach
//    void setUp() {
//        type = new LevelInitializer();
//        gameWorld = type.createNewGameWorld();
//        history = new CommandHistory();
//        programArea = new ProgramArea(gameWorld, history);
//
//        moveForward = new FunctionalBlock(new ActionFunctionality((MoveForwardAction) type.getAllActions().get(0)));
//        turnLeft = new FunctionalBlock(new ActionFunctionality((TurnLeftAction) type.getAllActions().get(1)));
//        turnRight = new FunctionalBlock(new ActionFunctionality((TurnRightAction) type.getAllActions().get(2)));
//        wallInFront = new PredicateBlock(new PredicateFunctionality((WallInFrontPredicate) type.getAllPredicates().get(0)));
//        notBlock = new NotBlock();
//        whileBlock = new WhileBlock();
//        ifBlock = new IfBlock();
//
//        paBlockHandler = new PABlockHandler(new ArrayList<>(Arrays.asList(moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock)), programArea);
//        converter = new IGUI_System_BlockLink();
//
//        cavityName = "Cavity";
//        functionalName = "functional";
//        conditionalName = "conditional";
//        operatorName = "operator";
//
//        cavity = new GUICavityBlock(cavityName, 0,0);
//        functional = new GUIFunctionalBlock(functionalName, 0,0);
//        conditional = new GUIConditionalBlock(conditionalName, 0,0);
//        operator = new GUIOperatorBlock(operatorName, 0,0);
//
//        blockHandlerController = new BlockHandlerController(converter, paBlockHandler);
//        connectionController = new ConnectionController(converter, paBlockHandler);
//
//        programAreaPanel = new ProgramAreaPanel(CORNER_X_PA, CORNER_Y_PA, WIDTH, HEIGHT, blockHandlerController, connectionController);
//        palette = new PalettePanel(CORNER_X_PALETTE, CORNER_Y_PALETTE, WIDTH, HEIGHT, new ArrayList<>(Arrays.asList(cavity, functional, conditional, operator)));
//
//        manager = new ProgramEventManager();
//        manager.subscribe(programAreaPanel);
//
//        historyController = new HistoryController(history, programArea);
//        guiBlockHandler = new GUIBlockHandler(palette, programAreaPanel, historyController);
//        start = new Position(100, 250);
//        end = new Position(500, 320);
//    }
//
//    @AfterEach
//    void tearDown() {
//        manager.unsubscribe(programAreaPanel);
//        manager = null;
//        programAreaPanel = null;
//        blockHandlerController = null;
//        connectionController = null;
//        converter = null;
//        paBlockHandler = null;
//        moveForward = null;
//        turnLeft = null;
//        turnRight = null;
//        wallInFront = null;
//        notBlock = null;
//        whileBlock = null;
//        ifBlock = null;
//        programArea = null;
//        history = null;
//        gameWorld = null;
//        type = null;
//        cavityName = null;
//        functionalName = null;
//        conditionalName = null;
//        operatorName = null;
//        cavity = null;
//        functional = null;
//        conditional = null;
//        operator = null;
//        palette = null;
//        block = null;
//        historyController = null;
//        guiBlockHandler = null;
//        start = null;
//        end = null;
//    }
//
//    @Test
//    void guiBlockHandler_invalidPalettePanel() {
//        assertThrows(IllegalArgumentException.class, () -> new GUIBlockHandler(null, programAreaPanel, historyController));
//    }
//
//    @Test
//    void guiBlockHandler_invalidProgramAreaPanel() {
//        assertThrows(IllegalArgumentException.class, () -> new GUIBlockHandler(palette, null, historyController));
//    }
//
//    @Test
//    void guiBlockHandler_invalidHistoryController() {
//        assertThrows(IllegalArgumentException.class, () -> new GUIBlockHandler(palette, programAreaPanel, null));
//    }
//
//    @Test
//    void handleMouseEventPre_released() {
//        guiBlockHandler.handleMouseEventPre(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        assertEquals(0, programAreaPanel.getBlocks().size());
//
//        guiBlockHandler.handleMouseEventPre(MouseEvent.MOUSE_RELEASED, CORNER_X_PA + WIDTH/2, CORNER_Y_PA + HEIGHT/2);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//        assertTrue(programAreaPanel.getBlocks().get(0) instanceof GUIFunctionalBlock);
//        assertEquals(CORNER_X_PA + WIDTH/2, programAreaPanel.getBlocks().get(0).getX());
//        assertEquals(CORNER_Y_PA + HEIGHT/2, programAreaPanel.getBlocks().get(0).getY());
//    }
//
//    @Test
//    void handleMouseEventPre_notReleased() {
//        guiBlockHandler.handleMouseEventPre(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        assertEquals(0, programAreaPanel.getBlocks().size());
//
//        guiBlockHandler.handleMouseEventPre(MouseEvent.MOUSE_DRAGGED, CORNER_X_PA + WIDTH/2, CORNER_Y_PA + HEIGHT/2);
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//        assertTrue(programAreaPanel.getBlocks().get(0) instanceof GUIFunctionalBlock);
//        assertEquals(CORNER_X_PA + WIDTH/2, programAreaPanel.getBlocks().get(0).getX());
//        assertEquals(CORNER_Y_PA + HEIGHT/2, programAreaPanel.getBlocks().get(0).getY());
//    }
//
//    @Test
//    void handleMouseEvent_paletteToProgramArea() {
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        assertEquals(0, programAreaPanel.getBlocks().size());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, CORNER_X_PA + WIDTH/2, CORNER_Y_PA + HEIGHT/2);
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//        assertTrue(programAreaPanel.getBlocks().get(0) instanceof GUIFunctionalBlock);
//        assertEquals(CORNER_X_PA + WIDTH/2, programAreaPanel.getBlocks().get(0).getX());
//        assertEquals(CORNER_Y_PA + HEIGHT/2, programAreaPanel.getBlocks().get(0).getY());
//    }
//
//    @Test
//    void handleMouseEvent_paletteToPalette() {
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        assertEquals(0, programAreaPanel.getBlocks().size());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, CORNER_X_PALETTE + WIDTH/2, CORNER_Y_PALETTE + HEIGHT/2);
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(0, programAreaPanel.getBlocks().size());
//    }
//
//    @Test
//    void handleMouseEvent_paletteToNowhere() {
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        assertEquals(0, programAreaPanel.getBlocks().size());
//
//        // To be sure it is outside the palette
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, Math.abs(CORNER_X_PA) + Math.abs(CORNER_X_PALETTE) + WIDTH * 2, CORNER_Y_PALETTE + HEIGHT/2);
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(0, programAreaPanel.getBlocks().size());
//    }
//
//    @Test
//    void handleMouseEvent_multipleFromPaletteToPA() {
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        assertEquals(0, programAreaPanel.getBlocks().size());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, CORNER_X_PA + WIDTH/2, (int) (CORNER_Y_PA + HEIGHT*0.25));
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//        assertTrue(programAreaPanel.getBlocks().get(0) instanceof GUIFunctionalBlock);
//        assertEquals(CORNER_X_PA + WIDTH/2, programAreaPanel.getBlocks().get(0).getX());
//        assertEquals((int) (CORNER_Y_PA + HEIGHT*0.25), programAreaPanel.getBlocks().get(0).getY());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, CORNER_X_PA + WIDTH/2, (int) (CORNER_Y_PA + HEIGHT*0.7));
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(2, programAreaPanel.getBlocks().size());
//        assertTrue(programAreaPanel.getBlocks().get(1) instanceof GUIFunctionalBlock);
//        assertEquals(CORNER_X_PA + WIDTH/2, programAreaPanel.getBlocks().get(1).getX());
//        assertEquals((int) (CORNER_Y_PA + HEIGHT*0.7), programAreaPanel.getBlocks().get(1).getY());
//    }
//
//    @Test
//    void handleMouseEvent_programAreaToProgramArea() {
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        assertEquals(0, programAreaPanel.getBlocks().size());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, CORNER_X_PA + WIDTH/2, (int) (CORNER_Y_PA + HEIGHT*0.25));
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//        assertTrue(programAreaPanel.getBlocks().get(0) instanceof GUIFunctionalBlock);
//        assertEquals(CORNER_X_PA + WIDTH/2, programAreaPanel.getBlocks().get(0).getX());
//        assertEquals((int) (CORNER_Y_PA + HEIGHT*0.25), programAreaPanel.getBlocks().get(0).getY());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, CORNER_X_PA + WIDTH/2, (int) (CORNER_Y_PA + HEIGHT*0.25));
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, CORNER_X_PA + WIDTH/2, (int) (CORNER_Y_PA + HEIGHT*0.7));
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//        assertTrue(programAreaPanel.getBlocks().get(0) instanceof GUIFunctionalBlock);
//        assertEquals(CORNER_X_PA + WIDTH/2, programAreaPanel.getBlocks().get(0).getX());
//        assertEquals((int) (CORNER_Y_PA + HEIGHT*0.7), programAreaPanel.getBlocks().get(0).getY());
//    }
//
//    @Test
//    void handleMouseEvent_programAreaToPalette() {
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        assertEquals(0, programAreaPanel.getBlocks().size());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, CORNER_X_PA + WIDTH/2, CORNER_Y_PA + HEIGHT/2);
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//        assertTrue(programAreaPanel.getBlocks().get(0) instanceof GUIFunctionalBlock);
//        assertEquals(CORNER_X_PA + WIDTH/2, programAreaPanel.getBlocks().get(0).getX());
//        assertEquals(CORNER_Y_PA + HEIGHT/2, programAreaPanel.getBlocks().get(0).getY());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, CORNER_X_PA + WIDTH/2, CORNER_Y_PA + HEIGHT/2);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, CORNER_X_PALETTE + WIDTH/2, CORNER_Y_PALETTE + HEIGHT/2);
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(0, programAreaPanel.getBlocks().size());
//    }
//
//    @Test
//    void handleMouseEvent_programAreaToNowhere() {
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        assertEquals(0, programAreaPanel.getBlocks().size());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, CORNER_X_PA + WIDTH/2, CORNER_Y_PA + HEIGHT/2);
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//        assertTrue(programAreaPanel.getBlocks().get(0) instanceof GUIFunctionalBlock);
//        assertEquals(CORNER_X_PA + WIDTH/2, programAreaPanel.getBlocks().get(0).getX());
//        assertEquals(CORNER_Y_PA + HEIGHT/2, programAreaPanel.getBlocks().get(0).getY());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, CORNER_X_PA + WIDTH/2, CORNER_Y_PA + HEIGHT/2);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//
//        // To be sure it is outside the palette
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, Math.abs(CORNER_X_PA) + Math.abs(CORNER_X_PALETTE) + WIDTH * 2, CORNER_Y_PALETTE + HEIGHT/2);
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//    }
//
//    @Test
//    void handleMouseEvent_connectedBlocks() {
//        assertEquals(0, programAreaPanel.getBlocks().size());
//        assertNull(programArea.getProgram());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        guiBlockHandler.handleMouseEventPre(MouseEvent.MOUSE_RELEASED, CORNER_X_PA + WIDTH/2, (int) (CORNER_Y_PA + HEIGHT*0.25));
//        assertEquals(1, programAreaPanel.getBlocks().size());
//        assertNotNull(programArea.getProgram());
//
//        int yPos = (int) (CORNER_Y_PA + HEIGHT*0.25) + programAreaPanel.getBlocks().get(0).getTotalHeight();
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        guiBlockHandler.handleMouseEventPre(MouseEvent.MOUSE_RELEASED, CORNER_X_PA + WIDTH/2, (int) (CORNER_Y_PA + HEIGHT*0.25) + programAreaPanel.getBlocks().get(0).getTotalHeight());
//        assertEquals(2, programAreaPanel.getBlocks().size());
//        assertNotNull(programArea.getProgram());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        guiBlockHandler.handleMouseEventPre(MouseEvent.MOUSE_RELEASED, CORNER_X_PA + WIDTH/2, (int) (CORNER_Y_PA + HEIGHT*0.25) + programAreaPanel.getBlocks().get(0).getTotalHeight());
//        assertEquals(3, programAreaPanel.getBlocks().size());
//        assertNotNull(programArea.getProgram());
//
//        GUIBlock top = programAreaPanel.getBlocks().get(0);
//        GUIBlock middle = programAreaPanel.getBlocks().get(1);
//        GUIBlock bottom = programAreaPanel.getBlocks().get(2);
//
//        assertEquals(3, top.getConnectedBlocks().size());
//        assertEquals(2, middle.getConnectedBlocks().size());
//        assertEquals(1, bottom.getConnectedBlocks().size());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, CORNER_X_PA + WIDTH/2, yPos);
//        guiBlockHandler.handleMouseEventPre(MouseEvent.MOUSE_RELEASED, CORNER_X_PA + WIDTH/2, yPos + 100);
//
//        assertEquals(3, programAreaPanel.getBlocks().size());
//        assertNull(programArea.getProgram());
//
//        assertEquals(1, top.getConnectedBlocks().size());
//        assertEquals(2, middle.getConnectedBlocks().size());
//        assertEquals(1, bottom.getConnectedBlocks().size());
//    }
//
//    @Test
//    void snapshot_backToPalette() {
//        Snapshot snapshot = guiBlockHandler.createSnapshot();
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        assertEquals(0, programAreaPanel.getBlocks().size());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, CORNER_X_PA + WIDTH/2, CORNER_Y_PA + HEIGHT/2);
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//        assertTrue(programAreaPanel.getBlocks().get(0) instanceof GUIFunctionalBlock);
//        assertEquals(CORNER_X_PA + WIDTH/2, programAreaPanel.getBlocks().get(0).getX());
//        assertEquals(CORNER_Y_PA + HEIGHT/2, programAreaPanel.getBlocks().get(0).getY());
//
//        guiBlockHandler.loadSnapshot(snapshot);
//        assertEquals(0, programAreaPanel.getBlocks().size());
//    }
//
//    @Test
//    void snapshot_backInProgramArea() {
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        assertEquals(0, programAreaPanel.getBlocks().size());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, CORNER_X_PA + WIDTH/2, (int) (CORNER_Y_PA + HEIGHT*0.25));
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//        assertTrue(programAreaPanel.getBlocks().get(0) instanceof GUIFunctionalBlock);
//        assertEquals(CORNER_X_PA + WIDTH/2, programAreaPanel.getBlocks().get(0).getX());
//        assertEquals((int) (CORNER_Y_PA + HEIGHT*0.25), programAreaPanel.getBlocks().get(0).getY());
//
//        Snapshot snapshot = guiBlockHandler.createSnapshot();
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, CORNER_X_PA + WIDTH/2, (int) (CORNER_Y_PA + HEIGHT*0.25));
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, CORNER_X_PA + WIDTH/2, (int) (CORNER_Y_PA + HEIGHT*0.7));
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//        assertTrue(programAreaPanel.getBlocks().get(0) instanceof GUIFunctionalBlock);
//        assertEquals(CORNER_X_PA + WIDTH/2, programAreaPanel.getBlocks().get(0).getX());
//        assertEquals((int) (CORNER_Y_PA + HEIGHT*0.7), programAreaPanel.getBlocks().get(0).getY());
//
//        guiBlockHandler.loadSnapshot(snapshot);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//        assertTrue(programAreaPanel.getBlocks().get(0) instanceof GUIFunctionalBlock);
//        assertEquals(CORNER_X_PA + WIDTH/2, programAreaPanel.getBlocks().get(0).getX());
//        assertEquals((int) (CORNER_Y_PA + HEIGHT*0.25), programAreaPanel.getBlocks().get(0).getY());
//    }
//
//    @Test
//    void snapshot_afterRemoveOfBlock() {
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, functional.getX(), functional.getY());
//        assertEquals(0, programAreaPanel.getBlocks().size());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, CORNER_X_PA + WIDTH/2, CORNER_Y_PA + HEIGHT/2);
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//        assertTrue(programAreaPanel.getBlocks().get(0) instanceof GUIFunctionalBlock);
//        assertEquals(CORNER_X_PA + WIDTH/2, programAreaPanel.getBlocks().get(0).getX());
//        assertEquals(CORNER_Y_PA + HEIGHT/2, programAreaPanel.getBlocks().get(0).getY());
//
//        Snapshot snapshot = guiBlockHandler.createSnapshot();
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_PRESSED, CORNER_X_PA + WIDTH/2, CORNER_Y_PA + HEIGHT/2);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, CORNER_X_PALETTE + WIDTH/2, CORNER_Y_PALETTE + HEIGHT/2);
//        guiBlockHandler.handleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0);
//        assertEquals(0, programAreaPanel.getBlocks().size());
//
//        guiBlockHandler.loadSnapshot(snapshot);
//        assertEquals(1, programAreaPanel.getBlocks().size());
//        assertTrue(programAreaPanel.getBlocks().get(0) instanceof GUIFunctionalBlock);
//        assertEquals(CORNER_X_PA + WIDTH/2, programAreaPanel.getBlocks().get(0).getX());
//        assertEquals(CORNER_Y_PA + HEIGHT/2, programAreaPanel.getBlocks().get(0).getY());
//    }
//}