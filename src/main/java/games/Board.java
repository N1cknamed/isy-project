package games;

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
    public void set(int x, int y, char c) {
        this.board[y][x] = c;
    }
    public char get(int x, int y) {
        return this.board[y][x];
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
