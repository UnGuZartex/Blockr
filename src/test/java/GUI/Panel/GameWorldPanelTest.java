package GUI.Panel;

// TODO class GameWorldPanelTest {
//
//    GameWorldPanel panel;
//    int cornerX, cornerY, width, height;
//    ProgramController controller;
//
//    @BeforeEach
//    void setUp() {
//        cornerX = 5;
//        cornerY = 8;
//        width = 500;
//        height = 800;
//        LevelInitializer init = new LevelInitializer();
//        Level level = (Level) init.createNewGameWorld();
//        PABlockHandler blockHandler = new PABlockHandler();
//        BlockLinkDatabase converter = new BlockLinkDatabase();
//        controller = new ProgramController(converter, blockHandler);
//        LevelLoader loader = new LevelLoader();
//        loader.loadLevel();
//        panel = new GameWorldPanel(cornerX, cornerY, width, height, controller);
//
//    }
//
//    @AfterEach
//    void tearDown() {
//        controller = null;
//        panel = null;
//    }
//
//    @Test
//    void getPanelRectangle() {
//        assertEquals(cornerX, panel.getPanelRectangle().getX());
//        assertEquals(cornerY, panel.getPanelRectangle().getY());
//        assertEquals(width, panel.getPanelRectangle().getWidth());
//        assertEquals(height, panel.getPanelRectangle().getHeight());
//    }
//
//    @Test
//    void getSize() {
//        assertEquals(width, panel.getSize().getX());
//        assertEquals(height, panel.getSize().getY());
//    }
//
//    @Test
//    void getLeftCorner() {
//        assertEquals(cornerX, panel.getLeftCorner().getX());
//        assertEquals(cornerY, panel.getLeftCorner().getY());
//    }
//
//    @Test
//    void resetGameText() {
//        panel.gameState = "randomText";
//        panel.resetGameText();
//        assertEquals("", panel.gameState);
//    }
//
//    @Test
//    void onGameWon() {
//        panel.onGameWon();
//        assertEquals("YOU WIN!  :)", panel.gameState);
//    }
//
//    @Test
//    void onGameLost() {
//        panel.onGameLost();
//        assertEquals("YOU LOSE!  :(", panel.gameState);
//    }
//
//    @Test
//    void onProgramReset() {
//        panel.onProgramReset();
//        assertEquals("", panel.gameState);
//    }
//
//    @Test
//    void onTooManyPrograms() {
//        panel.onTooManyPrograms();
//        assertEquals("TOO MANY PROGRAMS!", panel.gameState);
//    }
//
//    @Test
//    void onProgramInvalid() {
//        panel.onProgramInvalid();
//        assertEquals("INVALID PROGRAM", panel.gameState);
//    }
//}