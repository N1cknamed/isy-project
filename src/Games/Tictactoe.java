package Games;

import Ai.TttAI;

import java.util.*;

public class Tictactoe extends Board{
    private final char[][] board;
    private char player = 'x';
    public int[][] winningCoords;
    public Tictactoe() {
        super(3, 3);
        this.board = super.getBoard();
    }
    public Tictactoe(char[][] board, char player) {
        // make a game with existing board
        super(board);
        this.board = super.getBoard();
        this.player = player;
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
        // [row, col]
        int[][] winningCoords = new int[boardWidth][2];
        // row
        for (int i = 0; i < boardHeight; i++) {
            boolean won = true;
            for (int j = 0; j < boardWidth; j++){
                if (board[i][j] != player){
                    won = false;
                    break;
                } else {
                    winningCoords[j][0] = i;
                    winningCoords[j][1] = j;
                }
            }
            if (won) {
                this.winningCoords = winningCoords;
                return true;
            }
        }

        // col
        for (int i = 0; i < boardWidth; i++) {
            boolean won = true;
            for (int j = 0; j < boardHeight; j++){
                if (board[j][i] != player){
                    won = false;
                    break;
                } else {
                    winningCoords[j][0] = j;
                    winningCoords[j][1] = i;
                }
                
            }
            if (won) {
                this.winningCoords = winningCoords;
                return true;
            }
        }

        // diagonal left
        boolean won = true;
        for ( int i = 0; i < boardHeight; i++){
            if (board[i][i] != player){
                won = false;
                break;
            } else {
                winningCoords[i][0] = i;
                winningCoords[i][1] = i;
            }
        }
        if (won) {
            this.winningCoords = winningCoords;
            return true;
        }

        // diagonal right
        won = true;
        for ( int i = 0, j = boardHeight-1; i < boardHeight; i++, j--){
            if (board[i][j] != player){
                won = false;
                break;
            } else {
                winningCoords[i][0] = i;
                winningCoords[i][1] = j;
            }
        }
        if (won){
            this.winningCoords = winningCoords;
        }
        return won;
    }

    public void playGame() {
        Scanner in = new Scanner(System.in);
        boolean winner = false;
        System.out.println("Tic Tac Toe");

        TttAI ai = new TttAI('o', 'x');

        while (!winner) {
            printBoard();
            if (getPlayer() == 'x') {
                int inrow;
                int incol;

                System.out.printf("Turn for player %s\n", getPlayer());
                try {
                    System.out.print("Input row: ");
                    inrow = in.nextInt();
                    if (!(inrow >= 0 && inrow < boardHeight)) {
                        System.out.println("Invalid row input; retry input:");
                        continue;
                    }
                    System.out.print("Input col: ");
                    incol = in.nextInt();
                    if (!(incol >= 0 && incol < boardWidth)) {
                        System.out.println("Invalid col input; retry input:");
                        continue;
                    }
                    if (!move(inrow, incol)) {
                        System.out.println("Location is already taken; retry input:");
                        continue;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input; re-enter slot number:");
                    continue;
                }
            } else {
                System.out.println("AI's turn");
                int[] aiMove = ai.findBestMove(this);
                int aiRow = aiMove[1];
                int aiCol = aiMove[2];
                move(aiRow, aiCol);
            }

            if (checkWin(getPlayer())) {
                winner = true;
                printBoard();
                System.out.printf("Player %s has won", getPlayer());
            } else if (isBoardFull()) {
                winner = true;
                printBoard();
                System.out.println("It's a draw!");
            }

            switchPlayer();
        }
    }
}
