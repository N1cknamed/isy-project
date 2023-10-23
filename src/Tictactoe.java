import java.util.*;

public class Tictactoe {
    private final int boardWidth;
    private final int boardHeight;
    private final char[][] board;
    private char player = 'x';

    public Tictactoe() {
        this.boardWidth = 3; // this is so you can easily check if board wifth and height and if later we want to change board size
        this.boardHeight = 3;
        this.board = new char[boardHeight][boardWidth];
    }

    // for getting board into ui
    public char[][] getBoard() {
        return board;
    }

    public char getPlayer() {
        return player;
    }

    public void chancePlayer() {
        if (player == 'x'){
            player = 'o';
        } else {
            player = 'x';
        }
    }

    public boolean move(int x, int y, char player) {
        if (((x < 0) || (x >= boardWidth)) || ((y < 0) || (y >= boardWidth))){  // check if x or y are out of bounds
            return false;
        }
        if (board[x][y] == 0){ // check if index is empty, idk why this works
            board[x][y] = player;
            return true;
        }
        return false; // index is already full
    }

    public void printBoard() {
        System.out.println("      col");

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
            System.out.println(" |---|---|---|");
        }
    }

    public boolean checkWin(char player) {
        boolean won = true;
        // row
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++){
                if (board[i][j] != player){
                    won = false;
                    break;
                }
            }
            if (won) {
                return true;
            }
        }

        // col
        won = true;
        for (int i = 0; i < boardWidth; i++) {

            for (int j = 0; j < boardHeight; j++){
                if (board[j][i] != player){
                    won = false;
                    break;
                }
            }
            if (won) {
                return true;
            }
        }

        // diagonal left
        won = true;
        for ( int i = 0; i < boardHeight; i++){
            if (board[i][i] != player){
                won = false;
                break;
            }
        }
        if (won) {
            return true;
        }

        // diagonal right
        won = true;
        for ( int i = 0, j = boardHeight-1; i < boardHeight; i++, j--){
            if (board[i][j] != player){
                won = false;
                break;
            }
        }
        return won;
    }

    // just to test game
    public void playGame() {
        Scanner in = new Scanner(System.in);
        boolean winner = false;
        System.out.println("tic tac toe");

        while (!winner) {
            int inrow;
            int incol;

            printBoard();
            System.out.printf("turn for player %s\n", player);
            try {
                System.out.print("input row: ");
                inrow = in.nextInt();
                if (!(inrow >= 0 && inrow < boardHeight)) {
                    System.out.println("Invalid row input; retry input:");
                    continue;
                }
                System.out.print("input col: ");
                incol = in.nextInt();
                if (!(incol >= 0 && incol < boardWidth)) {
                    System.out.println("Invalid row input; retry input:");
                    continue;
                }
                if (!move(inrow,incol,player)){
                    System.out.println("location is already taken; retry input:");
                    continue;
                }
            }
            catch (InputMismatchException e) {
                System.out.println(
                        "Invalid input; re-enter slot number:");
                continue;
            }
            if (checkWin(player)){
                winner = true;
                printBoard();
                System.out.printf("player %s has won", player);
            }
            chancePlayer();

        }
    }
}
