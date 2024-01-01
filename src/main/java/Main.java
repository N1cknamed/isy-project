import Framework.Game;
import Framework.GameController;
import Framework.GameSubscriber;
import Gui.*;
import Games.*;
import Server.*;
import ttt.*;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        // TicTacToe Cli
//        Game game = new TicTacToeGame();
//        GameController controller = new GameController(game, TttAIPlayer::new, new TttPlayerFactory());
//        controller.registerSubscriber(new TttCliSubscriber());
//        controller.gameLoop();


        // TicTacToe Gui
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
