package GUI.Panel;

// TODO class PalettePanelTest {
//
//    PalettePanel panel;
//    int cornerX, cornerY, width, height;
//    ProgramController controller;
//
//    @BeforeEach
//    void setUp() {
//        cornerX = 5;
//        cornerY = 8;
//        width = 500;
//        height = 800;
//        PABlockHandler blockHandler = new PABlockHandler();
//        BlockLinkDatabase converter = new BlockLinkDatabase(blockHandler);
//        controller = new ProgramController(converter, blockHandler);
//        LevelLoader loader = new LevelLoader();
//        loader.loadLevel();
//        panel = new PalettePanel(cornerX, cornerY, width, height, controller);
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
//    void getBlocks() {
//        GameState.setMaxAmountOfBlocks(5);
//        panel.update();
//        assertEquals(7, panel.getBlocks().size());
//    }
//
//    @Test
//    void getNewBlock() {
//        GUIBlock block = panel.getNewBlock("IF", 2,3);
//        assertEquals(2, block.getX());
//        assertEquals(3, block.getY());
//        assertTrue(block instanceof GUICavityBlock);
//    }
//
//    @Test
//    void update() {
//        GameState.setMaxAmountOfBlocks(0);
//        panel.update();;
//        assertEquals(0, panel.getBlocks().size());
//
//        GameState.setMaxAmountOfBlocks(5);
//        panel.update();
//        assertEquals(7, panel.getBlocks().size());
//    }
//}