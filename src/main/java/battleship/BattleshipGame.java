package battleship;

import Framework.Game;
import Framework.Player;
import Framework.PlayerFactory;
import Games.Board;

import java.awt.*;

public class BattleshipGame implements Game {

    private final Board board1;
    private final Board board2;

    private BattleshipPlayer player1, player2;

    private char currentPlayer= '1';

    public BattleshipGame() {
        this.board1 = new Board(8, 8);
        this.board2 = new Board(8, 8);
    }

    @Override
    public void start(PlayerFactory playerFactory1, PlayerFactory playerFactory2) {
        this.player1 = (BattleshipPlayer) playerFactory1.build('1');
        this.player2 = (BattleshipPlayer) playerFactory2.build('2');
        this.player1.placeBoats();
        this.player2.placeBoats();
    }

    @Override
    public boolean hasEnded() {
        return false;
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer == '1' ? player1 : player2;
    }

    @Override
    public Board getBoard() {
        return null;
    }

    @Override
    public boolean doMove(Point move) {
        return false;
    }

    @Override
    public boolean isValidMove(Point move) {
        return false;
    }

    @Override
    public Player getWinner() {
        return null;
    }
}
