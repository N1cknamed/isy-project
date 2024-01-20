package battleship;

import battleship.players.BattleshipPlayer;
import framework.Game;
import framework.Player;
import framework.PlayerFactory;
import framework.Board;

import java.awt.Point;
import java.util.Collection;
import java.util.List;

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
        return !getCurrentPlayer().isAlive();
    }

    @Override
    public BattleshipPlayer getCurrentPlayer() {
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

    public Board getOpponentBoard() {
        return currentPlayer == '2' ? board1 : board2;
    }

    @Override
    public void doMove(Point move) {
        if (!isValidMove(move)) {
            throw new IllegalStateException("Called doMove() with an invalid move (" + move + ")");
        }

        char shot = getOpponentPlayer().shoot(move);
        getCurrentBoard().set(move.x, move.y, shot);
    }

    @Override
    public void nextPlayer() {
        currentPlayer = currentPlayer == '1' ? '2' : '1';
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
        return getAllPlayers().stream()
                .map(p -> (BattleshipPlayer) p)
                .filter(BattleshipPlayer::isAlive)
                .findFirst().orElseThrow();
    }

    @Override
    public Player getLoser() {
        BattleshipPlayer winner = (BattleshipPlayer) getWinner();
        return getAllPlayers().stream()
                .map(p -> (BattleshipPlayer) p)
                .filter(p -> p != winner)
                .findFirst().orElseThrow();
    }

    @Override
    public Collection<Player> getAllPlayers() {
        return List.of(player1, player2);
    }
}
