package games;

import java.util.Scanner;

public class CliPlayer implements Player {
    private final Scanner scanner;

    public CliPlayer() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    public int[] makeMove(Tictactoe game) {
        int row, col;
        do {
            System.out.printf("Player %s's turn\n", game.getPlayer());
            System.out.print("Enter row (0-2): ");
            row = scanner.nextInt();
            System.out.print("Enter column (0-2): ");
            col = scanner.nextInt();
        } while (!isValidMove(game, row, col));

        return new int[]{row, col};
    }

    @Override
    public void updateBoard(Tictactoe game) {
        game.printBoard();
    }

    private boolean isValidMove(Tictactoe game, int row, int col) {
        return (row >= 0 && row < game.getBoardHeight()) &&
                (col >= 0 && col < game.getBoardWidth()) &&
                game.isMovePosible(row, col);
    }
}