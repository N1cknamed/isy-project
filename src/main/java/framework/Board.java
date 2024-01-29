package framework;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class Board {
    final int boardWidth;
    final int boardHeight;
    private char[] board;

    public Board(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth; // this is so you can easily check if board width and height and if later we want to change board size
        this.boardHeight = boardHeight;
        this.board = new char[boardHeight * boardWidth];
    }

    public Board copy() {
        Board copy = new Board(boardWidth, boardHeight);

        char[] copyArray = new char[board.length];
        System.arraycopy(board, 0, copyArray, 0, board.length);
        copy.board = copyArray;

        return copy;
    }

    public void set(int x, int y, char c) {
        if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight) {
            return;
        }
        this.board[x + y * boardWidth] = c;
    }
    public void set(Point p, char c) {
        set(p.x, p.y, c);
    }
    public char get(int x, int y) {
        if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight) {
            return 0;
        }
        return this.board[x + y * boardWidth];
    }
    public char get(Point p) {
        return get(p.x, p.y);
    }
    public boolean isBoardFull() {
        for (char c : board) {
            if (c == 0) {
                return false;
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
        for (int y = 0; y < boardHeight; y++){
            System.out.print(y);
            System.out.print("|");
            for (int x = 0; x < boardWidth; x++){
                if (get(x, y) == 0){
                    System.out.print("   ");
                } else {
                    System.out.print(" "+get(x,y)+" ");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board1 = (Board) o;
        return boardWidth == board1.boardWidth && boardHeight == board1.boardHeight && Arrays.equals(board, board1.board);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(boardWidth, boardHeight);
        result = 31 * result + Arrays.hashCode(board);
        return result;
    }
}
