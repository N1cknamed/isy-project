import framework.Game;
import framework.GameController;
import gui.*;
import battleship.BattleshipCliSubscriber;
import battleship.BattleshipGame;
import battleship.BattleshipPlayerFactory;
import ttt.*;


public class Main {
    public static void main(String[] args) {

        // TicTacToe Cli
//        Game game = new TttGame();
//        GameController controller = new GameController(game, TttAiPlayer::new, new TttPlayerFactory());
//        controller.registerSubscriber(new TttCliSubscriber());
//        controller.gameLoop();

        Game game = new BattleshipGame();
        GameController controller = new GameController(game, new BattleshipPlayerFactory(), new BattleshipPlayerFactory());
        controller.registerSubscriber(new BattleshipCliSubscriber());
        controller.gameLoop();

        // tic tac toe gui (can choose to play against ai) TODO: reset game after switching game type
//        Game game = new TttGame();
//        GameController controller = new GameController(game, TttGuiPlayer::new, TttGuiPlayer::new);
//        Thread t = new Thread(() -> {
//            TttGui.launch(TttGui.class, args);
//        });
//        t.start();
//
//        controller.registerSubscriber(new TttCliSubscriber());
//        controller.registerSubscriber(new TttGuiSubscriber());
//        controller.gameLoop();

        // home gui
        HomeGui.launch(HomeGui.class,args);

    }
}
