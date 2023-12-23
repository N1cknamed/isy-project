package battleship;

import Framework.Game;
import Framework.Player;
import Framework.PlayerFactory;
import Games.Board;

import java.awt.*;

public class BattleshipGame implements Game {

    private final Board board1;
    private final Board board2;

    private char currentPlayer= '1';

    public BattleshipGame() {
        this.board1 = new Board(8, 8);
        this.board2 = new Board(8, 8);
    }

    @Override
    public void start(PlayerFactory playerFactory1, PlayerFactory playerFactory2) {

    }

    @Override
    public boolean hasEnded() {
        return false;
    }

    @Override
    public Player getCurrentPlayer() {
        return null;
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
