import battleship.*;
import framework.*;
import framework.server.*;
import gui.HomeGui;
import gui.TttGui;
import ttt.*;


public class Main {
    private static final String TEAM_NAME = "robbie";
    private static final PlayerType LOCAL_PLAYER = PlayerType.GUI;
    private static final String SERVER_HOST = "home.woutergritter.me";
    private static final int SERVER_PORT = 7789;

    public static void main(String[] args) {
//        runTttCli();
//        runBattleshipCli();
//        runBattleshipCsv();
//        runTttGui();
//        runHomeGui();

//        runServerTttCli();
        runServerBattleshipCli();
    }

    private static void runServerTttCli() {
        // Create a PlayerFactoryBuilder when we're starting the application and have a ServerController
        PlayerFactoryBuilder playerFactoryBuilder = Ttt.getPlayerFactoryBuilder();

        // Build the game classes and use the player types to create PlayerFactory objects
        ServerGameController controller = new ServerGameController(TttServerGame::new, "Tic-tac-toe", SERVER_HOST, SERVER_PORT, TEAM_NAME, playerFactoryBuilder.build(LOCAL_PLAYER), ServerPlayer::new);
        controller.registerSubscriber(new TttCliSubscriber());

        // Start the game
        controller.gameLoop();
    }

    private static void runServerBattleshipCli() {
        // Create a PlayerFactoryBuilder when we're starting the application and have a ServerController
//        PlayerFactoryBuilder playerFactoryBuilder = Ttt.getPlayerFactoryBuilder();

        // Build the game classes and use the player types to create PlayerFactory objects
        ServerGameController controller = new ServerGameController(BattleshipServerGame::new, "Battleship", SERVER_HOST, SERVER_PORT, TEAM_NAME, BattleshipRandomPlayer::new, BattleshipServerPlayer::new);
        controller.registerSubscriber(new BattleshipCliSubscriber());

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

        ServerGameController controller = new ServerGameController(TttServerGame::new, "Tic-tac-toe", SERVER_HOST, SERVER_PORT, TEAM_NAME, playerFactoryBuilder.build(LOCAL_PLAYER), ServerPlayer::new);
        Thread t = new Thread(() -> {
            TttGui.launch(TttGui.class);
        });
        t.start();

        // Handle challenge requests
        controller.setServerChallengeHandler(challenge -> {
            System.out.println("WE RECEIVED A CHALLENGE BY " + challenge.getChallenger());

            // TODO Open GUI popup or something to accept/decline the challenge
            boolean accept = false;

            if (accept) {
                challenge.accept();
            }
        });

        // Send challenges
        // controller.challengePlayer("albert");

        controller.registerSubscriber(new TttCliSubscriber());
        controller.registerSubscriber(new TttGuiSubscriber());
        controller.gameLoop();
    }

    private static void runHomeGui() {
        HomeGui.launch(HomeGui.class);
    }
}
