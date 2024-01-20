package framework;

import java.awt.*;

public class Board {
    final int boardWidth;
    final int boardHeight;
    private char[][] board;

    public Board(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth; // this is so you can easily check if board width and height and if later we want to change board size
        this.boardHeight = boardHeight;
        this.board = new char[boardHeight][boardWidth];
    }
    public Board(char[][] board) {
        // for creating a new board with existig board
        this.boardWidth = board.length; // this is so you can easily check if board width and height and if later we want to change board size
        this.boardHeight = board[0].length;
        this.board = board;
    }

    public Board copy() {
        Board copy = new Board(boardWidth, boardHeight);
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                copy.set(x, y, this.get(x, y));
            }
        }

        return copy;
    }

    public void set(int x, int y, char c) {
        if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight) {
            return;
        }
        this.board[y][x] = c;
    }
    public void set(Point p, char c) {
        set(p.x, p.y, c);
    }
    public char get(int x, int y) {
        if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight) {
            return 0;
        }
        return this.board[y][x];
    }
    public char get(Point p) {
        return get(p.x, p.y);
    }
    public char[][] getBoard() {
        return board;
    }
    public void setBoard(char[][] board) {
        this.board = board;
    }
    public boolean isBoardFull() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    public void printBoard() {
        System.out.println(" ".repeat((1 + boardWidth*4)/2) + "col");

        System.out.print(" |");
        for (int i = 0; i < boardWidth; i++){
            System.out.printf("_%s_|",i);
        }

        System.out.println();
        for (int i = 0; i < boardHeight; i++){
            System.out.print(i);
            System.out.print("|");
            for (int j = 0; j < boardWidth; j++){
                if (board[i][j] == 0){
                    System.out.print("   ");
                } else {
                    System.out.print(" "+board[i][j]+" ");
                }
                System.out.print("|");
            }
            System.out.println();
            System.out.println(" |"+ "---|".repeat(boardWidth));
        }
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }
}
