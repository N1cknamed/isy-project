import Framework.Game;
import Framework.GameController;
import Gui.*;
import Ai.*;
import Games.*;
import Server.*;
import ttt.TicTacToeGame;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Game game = new TicTacToeGame();
        GameController controller = new GameController(game);
    }
}
