import Framework.Game;
import Framework.GameController;
import Framework.GameSubscriber;
import Gui.*;
import Games.*;
import Server.*;
import battleship.BattleshipCliSubscriber;
import battleship.BattleshipGame;
import battleship.BattleshipPlayerFactory;
import ttt.TicTacToeGame;
import ttt.TttAIPlayer;
import ttt.TttCLIPlayer;
import ttt.TttCliSubscriber;
import ttt.TttPlayerFactory;
import ttt.*;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        // TicTacToe Cli
//        Game game = new TicTacToeGame();
//        GameController controller = new GameController(game, TttAIPlayer::new, new TttPlayerFactory());
//        controller.registerSubscriber(new TttCliSubscriber());
//        controller.gameLoop();

        Game game = new BattleshipGame();
        GameController controller = new GameController(game, new BattleshipPlayerFactory(), new BattleshipPlayerFactory());
        controller.registerSubscriber(new BattleshipCliSubscriber());
        controller.gameLoop();

        // tic tac toe gui (can choose to play against ai) TODO: reset game after switching game type
//        Game game = new TicTacToeGame();
//        GameController controller = new GameController(game, TttGuiPlayer::new, TttGuiPlayer::new);
//        Thread t = new Thread(() -> {
//            TictactoeGui.launch(TictactoeGui.class, args);
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
