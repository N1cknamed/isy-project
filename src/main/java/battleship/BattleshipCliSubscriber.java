package battleship;

import Framework.Game;
import Framework.GameSubscriber;
import Framework.Player;

import java.awt.*;

public class BattleshipCliSubscriber implements GameSubscriber {
    @Override
    public void onGameStarted(Game game) {
        System.out.println("game started");
    }

    @Override
    public void onGameUpdated(Game game) {
        System.out.println("game updated");
        game.getBoard().printBoard();
    }

    @Override
    public void onGameEnded(Game game) {
        System.out.println("game ended");
    }

    @Override
    public void onPlayerMove(Player player, Point move) {
        System.out.println("player move");
    }
}
