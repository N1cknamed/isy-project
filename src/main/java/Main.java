import battleship.*;
import framework.*;
import gui.HomeGui;
import gui.TttGui;
import ttt.*;


public class Main {
    private static final String TEAM_NAME = "groep2";

    public static void main(String[] args) {
//        runTttCli();
//        runBattleshipCli();
        runBattleshipCsv();
//        runTttGui();
//        runHomeGui();

//        runServerTttCli();
//        runServerTttGui();
    }

    private static void runServerTttCli() {
        // Create a PlayerFactoryBuilder when we're starting the application and have a ServerController
        PlayerFactoryBuilder playerFactoryBuilder = Ttt.getPlayerFactoryBuilder();

        // Change the player types according to what the user wants (GUI buttons)
        PlayerType playerType = PlayerType.CLI;

        // Build the game classes and use the player types to create PlayerFactory objects
        ServerGame game = new TttServerGame();
        ServerGameController controller = new ServerGameController(game, "192.168.137.1", 7789, TEAM_NAME, playerFactoryBuilder.build(playerType));
        controller.registerSubscriber(new TttCliSubscriber());

        // Start the game
        controller.gameLoop();
    }

    private static void runTttCli() {
        // Create a PlayerFactoryBuilder when we're starting the application and have a ServerController
        PlayerFactoryBuilder playerFactoryBuilder = Ttt.getPlayerFactoryBuilder();

        // Change the player types according to what the user wants (GUI buttons)
        PlayerType player1Type = PlayerType.AI;
        PlayerType player2Type = PlayerType.CLI;

        // Build the game classes and use the player types to create PlayerFactory objects
        Game game = new TttGame();
        GameController controller = new GameController(game, playerFactoryBuilder.build(player1Type), playerFactoryBuilder.build(player2Type));
        controller.registerSubscriber(new TttCliSubscriber());
        GameSubscriber battleshipCsvSubscriber = new BattleshipCsvSubscriber();
        controller.registerSubscriber(battleshipCsvSubscriber);

        // Start the game
        controller.gameLoop();
    }

    private static void runBattleshipCli() {
        Game game = new BattleshipGame();
        GameController controller = new GameController(game, BattleshipRandomPlayer::new, BattleshippBoatPlacementPlayer::new);
//        GameController controller = new GameController(game, BattleshipAiPlayer::new, new BattleshipPlayerFactory());
        controller.registerSubscriber(new BattleshipCliSubscriber());
        controller.gameLoop();
    }

    private static void runBattleshipCsv() {
        for (int i = 0; i < 1000; i++) {
            Game game = new BattleshipGame();
            GameController controller = new GameController(game, BattleshipRandomPlayer::new, BattleshippBoatPlacementPlayer::new);
//            GameController controller = new GameController(game, BattleshipRandomPlayer::new, new BattleshipPlayerFactory());
            GameSubscriber csv = new BattleshipCsvSubscriber();
            controller.registerSubscriber(csv);
//            controller.registerSubscriber(new BattleshipCliSubscriber());
            controller.gameLoop();
        }
    }

    private static void runTttGui() {
        Game game = new TttGame();
        GameController controller = new GameController(game, TttGuiPlayer::new, TttGuiPlayer::new);
        Thread t = new Thread(() -> {
            TttGui.launch(TttGui.class);
        });
        t.start();

        controller.registerSubscriber(new TttCliSubscriber());
        controller.registerSubscriber(new TttGuiSubscriber());
        controller.gameLoop();
    }

    private static void runServerTttGui() {
        PlayerFactoryBuilder playerFactoryBuilder = Ttt.getPlayerFactoryBuilder();
        PlayerType playerType = PlayerType.GUI;

        ServerGame game = new TttServerGame();
        ServerGameController controller = new ServerGameController(game, "192.168.137.1", 7789, TEAM_NAME, playerFactoryBuilder.build(playerType));
        Thread t = new Thread(() -> {
            TttGui.launch(TttGui.class);
        });
        t.start();

        controller.registerSubscriber(new TttCliSubscriber());
        controller.registerSubscriber(new TttGuiSubscriber());
        controller.gameLoop();
    }

    private static void runHomeGui() {
        HomeGui.launch(HomeGui.class);
    }
}
