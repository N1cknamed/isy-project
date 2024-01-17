package battleship;

import framework.Board;
import framework.Game;
import framework.GameSubscriber;
import framework.Player;

import java.awt.*;

public class BattleshipCsvSubscriber implements GameSubscriber {
    BattleshipPlayer playerBoatPlacement = null;
    BattleshipPlayer PlayerShootingAlgorithm = null;
    @Override
    public void onGameStarted(Game game) {
        BattleshipGame battleshipGame = (BattleshipGame) game;
        this.playerBoatPlacement = (BattleshipPlayer) battleshipGame.getCurrentPlayer();
        this.PlayerShootingAlgorithm = (BattleshipPlayer) battleshipGame.getOpponentPlayer();
    }

    @Override
    public void onGameUpdated(Game game) {

    }

    @Override
    public void onGameEnded(Game game) {
        Board board = game.getBoard();
        board.printBoard();
    }

    @Override
    public void onPlayerMove(Player player, Point move) {

    }
}
