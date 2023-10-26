public class Board {
    final int boardWidth;
    final int boardHeight;
    private final char[][] board;

    public Board(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth; // this is so you can easily check if board width and height and if later we want to change board size
        this.boardHeight = boardHeight;
        this.board = new char[boardHeight][boardWidth];
    }
    public char[][] getBoard() {
        return board;
    }

    public boolean isBoardFull() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == ' ') {
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
            for (int j = 0; j < boardHeight; j++){
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
}
