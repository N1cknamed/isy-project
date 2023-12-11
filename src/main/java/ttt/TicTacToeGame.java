package ttt;

import Framework.Game;
import Framework.Player;
import Framework.PlayerFactory;
import Games.Board;

import java.awt.*;

public class TicTacToeGame implements Game {

    private final Board board;

    private char currentPlayer = 'x';
    private Player player1, player2;

    public TicTacToeGame(int width, int height) {
        this.board = new Board(width, height);
    }

    public TicTacToeGame() {
        this(3, 3);
    }


    @Override
    public void start(PlayerFactory playerFactory1, PlayerFactory playerFactory2) {
        this.player1 = playerFactory1.build('x');
        this.player2 = playerFactory2.build('o');
    }

    @Override
    public boolean hasEnded() {
        return board.isBoardFull() || checkWin('x') || checkWin('o');
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer == 'x' ? player1 : player2;
    }

    @Override
    public Board getBoard() {
        return this.board;
    }

    public char getCurrentPlayerSymbol() {
        return currentPlayer;
    }

    @Override
    public boolean doMove(Point move) {
        if (((move.x < 0) ||
                (move.x >= board.getBoardWidth())) ||
                ((move.y < 0) || (move.y >= board.getBoardWidth()))) {  // check if x or y are out of bounds
            return true;
        }
        if (board.get(move.x, move.y) == 0) { // check if index is empty, idk why this works
            board.set(move.x, move.y, currentPlayer);
            currentPlayer = currentPlayer == 'x' ? 'o' : 'x';
            return false;
        }
        return true; // index is already full
    }

    @Override
    public boolean isValidMove(Point move) {
        if (((move.x < 0) || (move.x >= board.getBoardWidth())) || ((move.y < 0) || (move.y >= board.getBoardHeight()))) {  // check if x or y are out of bounds
            return false;
        }
        return board.get(move.x,move.y) == 0;
    }

    @Override
    public Player getWinner() {
        if (checkWin('x')) {
            return player1;
        } else if (checkWin('o')) {
            return player2;
        } else {
            return null;
        }
    }

    public boolean checkWin(char player) {
        // [row, col]
//        int[][] winningCoords = new int[boardWidth][2];
        Point winningCoords = null;
        // row
        for (int y = 0; y < board.getBoardHeight(); y++) {
            boolean won = true;
            for (int x = 0; x < board.getBoardWidth(); x++) {
                if (board.get(x, y) != player) {
                    won = false;
                    break;
//                } else {
//                    winningCoords[x][0] = y;
//                    winningCoords[x][1] = x;
                }
            }
            if (won) {
//                this.winningCoords = winningCoords;
                return true;
            }
        }

        // col
        for (int x = 0; x < board.getBoardWidth(); x++) {
            boolean won = true;
            for (int y = 0; y < board.getBoardHeight(); y++) {
                if (board.get(x, y) != player) {
                    won = false;
                    break;
//                } else {
//                    winningCoords[y][0] = y;
//                    winningCoords[y][1] = x;
                }

            }
            if (won) {
//                this.winningCoords = winningCoords;
                return true;
            }
        }

        // diagonal left
        boolean won = true;
        for (int i = 0; i < board.getBoardHeight(); i++) {
            if (board.get(i, i) != player) {
                won = false;
                break;
//            } else {
//                winningCoords[i][0] = i;
//                winningCoords[i][1] = i;
            }
        }
        if (won) {
//            this.winningCoords = winningCoords;
            return true;
        }

        // diagonal right
        // TODO refactor?? board.get(2-i, i)
        won = true;
        for (int i = 0, j = board.getBoardHeight() - 1; i < board.getBoardHeight(); i++, j--) {
            if (board.get(j, i) != player) {
                won = false;
                break;
//            } else {
//                winningCoords[i][0] = i;
//                winningCoords[i][1] = j;
            }
        }
        if (won) {
//            this.winningCoords = winningCoords;
            return true;
        }

        return false;
    }
}
