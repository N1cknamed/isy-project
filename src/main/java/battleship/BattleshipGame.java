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

    private char currentPlayer = '1';

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

    public BattleshipPlayer getOpponentPlayer() {
        return currentPlayer == '1' ? player2 : player1;
    }

    @Override
    public Board getBoard() {
        return getCurrentBoard();
    }

    public Board getCurrentBoard() {
        return currentPlayer == '1' ? board1 : board2;
    }

    @Override
    public boolean doMove(Point move) {
        if (isValidMove(move)) {
            char shot = getOpponentPlayer().shoot(move);
            getCurrentBoard().set(move.x, move.y, shot);
            if (shot == 'm') {
                currentPlayer = currentPlayer == '1' ? '2' : '1';
            }
            return false;
        } else return true;
    }

    @Override
    public boolean isValidMove(Point move) {
        Board currentBoard = getCurrentBoard();
        // check if x or y are out of bounds
        return ((move.x >= 0) &&
                (move.x < currentBoard.getBoardWidth())) &&
                ((move.y >= 0) && (move.y < currentBoard.getBoardWidth()));
    }

    @Override
    public Player getWinner() {
        return null;
    }
}
