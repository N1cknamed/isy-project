import Framework.Game;
import Framework.GameController;
import Framework.GameSubscriber;
import Gui.*;
import Ai.*;
import Games.*;
import Server.*;
import ttt.TicTacToeGame;
import ttt.TttCLIPlayer;
import ttt.TttCliSubscriber;
import ttt.TttPlayerFactory;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Game game = new TicTacToeGame();
        GameController controller = new GameController(game, new TttPlayerFactory('x'), new TttPlayerFactory('o'));
        controller.registerSubscriber(new TttCliSubscriber());
        controller.gameLoop();
    }
}
