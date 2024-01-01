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

//        Game game = new TicTacToeGame();
//        GameController controller = new GameController(game, TttAIPlayer::new, new TttPlayerFactory());
//        controller.registerSubscriber(new TttCliSubscriber());
//        controller.gameLoop();

        // tic tac toe gui (can choose to play against ai) TODO: reset game after switching game type

        Game game = new TicTacToeGame();
        GameController controller = new GameController(game, TttGuiPlayer::new, TttGuiPlayer::new);
        //run this in seperate thread "TictactoeGui.launch(TictactoeGui.class, args);"
        Thread t = new Thread(() -> {
            TictactoeGui.launch(TictactoeGui.class, args);
        });
        t.start();
//        try {
//            Thread.sleep(1000); // Main thread sleeps for 1 second
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        controller.registerSubscriber(new TttCliSubscriber());
        controller.registerSubscriber(new TttGuiSubscriber());
        controller.gameLoop();

//        HomeGui.launch(HomeGui.class,args);

    }
}
