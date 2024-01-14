import battleship.BattleshipCliSubscriber;
import battleship.BattleshipGame;
import battleship.BattleshipPlayerFactory;
import framework.Game;
import framework.GameController;
import framework.PlayerFactoryBuilder;
import framework.PlayerType;
import gui.HomeGui;
import gui.TttGui;
import server.ServerController;
import ttt.*;


public class Main {
    public static void main(String[] args) {
//        runTttCli();
//        runBattleshipCli();
//        runTttGui();
        runHomeGui();
    }

    private static void runTttCli() {
        // Create a PlayerFactoryBuilder when we're starting the application and have a ServerController
        ServerController serverController = null;
        PlayerFactoryBuilder playerFactoryBuilder = Ttt.getPlayerFactoryBuilder()
                .withServerController(serverController);

        // Change the player types according to what the user wants (GUI buttons)
        PlayerType player1Type = PlayerType.AI;
        PlayerType player2Type = PlayerType.CLI;

        // Build the game classes and use the player types to create PlayerFactory objects
        Game game = new TttGame();
        GameController controller = new GameController(game, playerFactoryBuilder.build(player1Type), playerFactoryBuilder.build(player2Type));
        controller.registerSubscriber(new TttCliSubscriber());

        // Start the game
        controller.gameLoop();
    }

    private static void runBattleshipCli() {
        Game game = new BattleshipGame();
        GameController controller = new GameController(game, new BattleshipPlayerFactory(), new BattleshipPlayerFactory());
        controller.registerSubscriber(new BattleshipCliSubscriber());
        controller.gameLoop();
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

    private static void runHomeGui() {
        HomeGui.launch(HomeGui.class);
    }
}
