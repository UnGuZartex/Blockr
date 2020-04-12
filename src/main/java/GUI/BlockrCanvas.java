package GUI;

import Controllers.ControllerClasses.ConnectionController;
import Controllers.ControllerClasses.ProgramController;
import Controllers.Controls.Control;
import Controllers.Controls.ProgramStepCommand;
import Controllers.Controls.ResetControlFunctionality;
import Controllers.blockLinkDatabase;
import Controllers.Initialiser;
import Controllers.JarLoader;
import GUI.Blocks.GUIBlock;
import GUI.Components.GUIBlockHandler;
import GUI.Panel.GameWorldPanel;
import GUI.Panel.PalettePanel;
import GUI.Panel.ProgramAreaPanel;
import GameWorldAPI.GameWorldType.GameWorldType;
import System.Logic.ProgramArea.PABlockHandler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class BlockrCanvas extends CanvasWindow {

    public static final double PALETTE_WIDTH_RATIO = 0.1;
    public static final double PROGRAM_AREA_WIDTH_RATIO = 0.5;
    public static final double GAME_WORLD_WIDTH_RATIO = 0.4;

    private PalettePanel palettePanel;
    private ProgramAreaPanel programAreaPanel;
    private GameWorldPanel gameWorldPanel;
    private GUIBlockHandler blockHandler;
  
    private Control[] controls;
    private GUIBlock previousBlock;
    private final GameWorldType gameWorldType;

    private ProgramController programController;
    private ConnectionController connectionController;

    /**
     * Initializes a CanvasWindow object. 
     *
     * @param title Window title
     */
    // TODO exception throw (@throws)
    protected BlockrCanvas(String title, int width, int height) throws IOException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super(title);

        this.width = width;
        this.height = height;

        JarLoader loader = new JarLoader();
        gameWorldType = loader.load();

        setControllers();
        setPanels();
        setControls();
        setBlockHandler();
    }

    private void setPanels() {
        palettePanel = new PalettePanel(0, 0, (int)(width * PALETTE_WIDTH_RATIO), height);
        programAreaPanel = new ProgramAreaPanel((int)(width * PALETTE_WIDTH_RATIO),0, (int)(width * PROGRAM_AREA_WIDTH_RATIO), height, programController, connectionController);
        gameWorldPanel = new GameWorldPanel((int)(width * PALETTE_WIDTH_RATIO) + (int)(width * PROGRAM_AREA_WIDTH_RATIO),0, (int)(width * GAME_WORLD_WIDTH_RATIO), height, programController);
    }

    private void setControls() {
        controls = new Control[] {
                new Control(KeyEvent.VK_F5, new ProgramStepCommand(programController)),
                new Control(KeyEvent.VK_ESCAPE, new ResetControlFunctionality(programController, gameWorldPanel))
        };
    }

    private void setBlockHandler() {
        blockHandler = new GUIBlockHandler(palettePanel, programAreaPanel);
    }

    @Override
    protected void paint(Graphics g) {
        g.setColor(Color.black);
        programAreaPanel.paint(g);
        gameWorldPanel.paint(g);
        palettePanel.paint(g);
        programAreaPanel.drawBlocks(g);
        palettePanel.drawBlocks(g);
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        super.handleMouseEvent(id, x, y, clickCount);
        blockHandler.handleMouseEvent(id, x, y);
        repaint();
    }

    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        for (Control control : controls) {
            if (control.isClicked(keyCode)) {
                control.onClick();
            }
        }
        repaint();
    }

    private void setControllers() {
        Initialiser initialiser = new Initialiser(gameWorldType, gameWorldType.createNewGameworld());
        PABlockHandler blockHandler = new PABlockHandler(initialiser.getSystemPaletteBlocks());
        blockLinkDatabase converter = new blockLinkDatabase(blockHandler);
        connectionController = new ConnectionController(converter, blockHandler);
        programController = new ProgramController(converter, blockHandler);
    }
}