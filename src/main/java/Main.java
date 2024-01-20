import battleship.*;
import battleship.players.BattleshippBoatPlacementPlayer;
import battleship.players.BattleshipRandomPlayer;
import battleship.subscribers.BattleshipCliSubscriber;
import battleship.subscribers.BattleshipCsvSubscriber;
import framework.*;
import framework.server.*;
import gui.HomeGui;
import gui.TttGui;
import ttt.*;
import ttt.Subscribers.TttCliSubscriber;
import ttt.Subscribers.TttGuiSubscriber;
import ttt.players.TttGuiPlayer;


public class Main {
    private static final String TEAM_NAME = "wouter";
    private static final PlayerType LOCAL_PLAYER = PlayerType.GUI;

    public static void main(String[] args) {
//        runTttCli();
//        runBattleshipCli();
//        runBattleshipCsv();
//        runTttGui();
//        runHomeGui();

//        runServerTttCli();
        runServerTttGui();
    }

    private static void runServerTttCli() {
        // Create a PlayerFactoryBuilder when we're starting the application and have a ServerController
        PlayerFactoryBuilder playerFactoryBuilder = Ttt.getPlayerFactoryBuilder();

        // Build the game classes and use the player types to create PlayerFactory objects
        ServerGameController controller = new ServerGameController(TttServerGame::new, "Tic-tac-toe", "192.168.137.1", 7789, TEAM_NAME, playerFactoryBuilder.build(LOCAL_PLAYER));
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
//            GameController controller = new GameController(game, BattleshipRandomPlayer::new, BattleshippBoatPlacementPlayer::new);
//            GameController controller = new GameController(game, BattleshipRandomPlayer::new, new BattleshipPlayerFactory());
            GameController controller = new GameController(game, BattleshippBoatPlacementPlayer::new, new BattleshipPlayerFactory());
//            GameSubscriber csv = new BattleshipCsvSubscriber();
//            controller.registerSubscriber(csv);
            controller.registerSubscriber(new BattleshipCliSubscriber());
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

        ServerGameController controller = new ServerGameController(TttServerGame::new, "Tic-tac-toe", "192.168.137.1", 7789, TEAM_NAME, playerFactoryBuilder.build(LOCAL_PLAYER));
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
