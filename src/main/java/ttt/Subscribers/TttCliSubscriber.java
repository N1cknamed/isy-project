package ttt.Subscribers;

import framework.Game;
import framework.GameSubscriber;
import framework.Player;

import java.awt.*;

public class TttCliSubscriber implements GameSubscriber {
    @Override
    public void onGameStarted(Game game) {
        game.getBoard().printBoard();
    }

    @Override
    public void onGameUpdated(Game game) {
        game.getBoard().printBoard();
    }

    @Override
    public void onGameEnded(Game game) {
        if (game.getWinner() == null) {
            System.out.println("Draw");
        } else {
            System.out.println("player " + game.getWinner().getSymbol() + " has won.");
        }
        
    }

    @Override
    public void onPlayerMove(Player player, Point move) {
    }
}
