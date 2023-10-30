import java.util.*;

public class Tictactoe extends Board{
    private final char[][] board;
    private char player = 'x';
    public Tictactoe() {
        super(3, 3);
        board = super.getBoard();
    }
    public char getPlayer() {
        return player;
    }
    public void switchPlayer() {
        if (player == 'x'){
            player = 'o';
        } else {
            player = 'x';
        }
    }
    public boolean move(int x, int y) {
        if (((x < 0) || (x >= boardWidth)) || ((y < 0) || (y >= boardWidth))){  // check if x or y are out of bounds
            return false;
        }
        if (board[x][y] == 0){ // check if index is empty, idk why this works
            board[x][y] = player;
            return true;
        }
        return false; // index is already full
    }
    public boolean checkWin(char player) {
        int[][] winningCoords = new int[boardWidth][2];
        // row
        for (int i = 0; i < boardHeight; i++) {
            boolean won = true;
            for (int j = 0; j < boardWidth; j++){
                if (board[i][j] != player){
                    won = false;
                    break;
                    
                }
                winningCoords[i][0] = i;
                winningCoords[i][1] = j;
            }
            if (won) {
                for (int d = 0; d < winningCoords.length; d++) {
                  System.out.println(winningCoords[i][0] + " " + winningCoords[i][1]);
                }
                return true;
            }
        }

        // col
        for (int i = 0; i < boardWidth; i++) {
            won = true;
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
        for ( int i = 0; i < boardHeight; i++){
            won = true;
            if (board[i][i] != player){
                won = false;
                break;
            }
        }
        if (won) {
            return true;
        }

        // diagonal right
        for ( int i = 0, j = boardHeight-1; i < boardHeight; i++, j--){
            won = true;
            if (board[i][j] != player){
                won = false;
                break;
            }
        }
        return true
        // TODO
        // return won;
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
                if (!move(inrow,incol)){
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
            switchPlayer();

        }
    }
}
