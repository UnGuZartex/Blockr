package GUI.Components;

import Controllers.BlockLinkDatabase;
import Controllers.ControllerClasses.BlockHandlerController;
import Controllers.ControllerClasses.ConnectionController;
import Controllers.ControllerClasses.HistoryController;
import Controllers.ProgramEventManager;
import GUI.Blocks.*;
import GUI.Panel.PalettePanel;
import GUI.Panel.ProgramAreaPanel;
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
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.ProgramArea;
import Utility.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MoveBlockCommandTest {

    ProgramAreaPanel panel;
    PalettePanel palette;
    int cornerX, cornerY, width, height;
    Random random;
    static final int MIN_X = 0, MAX_X = 150, MIN_Y = 0, MAX_Y = 150;
    static final int MIN_WIDTH = 100, MAX_WIDTH = 800, MIN_HEIGHT = 100, MAX_HEIGHT = 800;
    BlockHandlerController blockHandlerController;
    ConnectionController connectionController;
    BlockLinkDatabase converter;
    PABlockHandler paBlockHandler;
    ProgramArea programArea;
    GameWorldType type;
    GameWorld gameWorld;
    Block moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock;
    CommandHistory history;
    GUIBlock cavity, functional, conditional, operator;
    String cavityName, functionalName, conditionalName, operatorName;
    ProgramEventManager manager;
    GUIBlock block;
    GUIBlockHandler handler;
    HistoryController historyController;
    Position start, end;

    @BeforeEach
    void setUp() {
        random = new Random();
        cornerX = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        cornerY = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;
        width = random.nextInt(MAX_WIDTH + 1 - MIN_WIDTH) + MIN_WIDTH;
        height = random.nextInt(MAX_HEIGHT + 1 - MIN_HEIGHT) + MIN_HEIGHT;

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
        converter = new BlockLinkDatabase();

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

        panel = new ProgramAreaPanel(cornerX, cornerY, width, height, blockHandlerController, connectionController);
        palette = new PalettePanel(cornerX, cornerY, width, height, new ArrayList<>(Arrays.asList(cavity, functional, conditional, operator)));

        manager = new ProgramEventManager();
        manager.subscribe(panel);

        block = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair = new AbstractMap.SimpleEntry<>(block, 0);
        panel.setTemporaryBlockPair(blockPair);
        panel.addTemporaryBlockToProgramArea();

        historyController = new HistoryController(history, programArea);
        handler = new GUIBlockHandler(palette, panel, historyController);
        start = new Position(100, 250);
        end = new Position(500, 320);
    }

    @AfterEach
    void tearDown() {
        manager.unsubscribe(panel);
        manager = null;
        random = null;
        panel = null;
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
        block = null;
        historyController = null;
        handler = null;
        start = null;
        end = null;
    }

    @Test
    void moveBlockCommand_invalidStart() {
        assertThrows(IllegalArgumentException.class, () -> new MoveBlockCommand(null, end, handler));
    }

    @Test
    void moveBlockCommand_invalidEnd() {
        assertThrows(IllegalArgumentException.class, () -> new MoveBlockCommand(start, null, handler));
    }

    @Test
    void moveBlockCommand_invalidGuiBlockHandler() {
        assertThrows(IllegalArgumentException.class, () -> new MoveBlockCommand(start, end, null));
    }
}