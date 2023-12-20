package Games;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BattleShips extends Board {
    private final char[][] board;
    private char player = 'x';
    private final Set<String> shipLocations;
    private final Set<String> hitLocations;
    private final Set<String> missedShotLocations;
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 10;
    private static final int NUM_SHIPS = 5;

    public BattleShips() {
        super(BOARD_WIDTH, BOARD_HEIGHT);
        this.board = super.getBoard();
        this.shipLocations = new HashSet<>();
        this.hitLocations = new HashSet<>();
        this.missedShotLocations = new HashSet<>();
        initializeShips();
    }

    private void initializeShips() {
        Random random = new Random();
        for (int i = 0; i < NUM_SHIPS; i++) {
            int shipRow = random.nextInt(boardHeight);
            int shipCol = random.nextInt(boardWidth);
            String shipLocation = String.format("%d,%d", shipRow, shipCol);
            if (shipLocations.contains(shipLocation)) {
                i--; // Try again to avoid overlapping ships
            } else {
                shipLocations.add(shipLocation);
            }
        }
    }

    public char getPlayer() {
        return player;
    }

    public void switchPlayer() {
        player = (player == 'x') ? 'o' : 'x';
    }

    public boolean makeMove(int x, int y) {
        if (isOutOfBounds(x, y) || isAlreadyTargeted(x, y)) {
            return false;
        }

        String moveLocation = String.format("%d,%d", x, y);

        if (shipLocations.contains(moveLocation)) {
            hitLocations.add(moveLocation);
        } else {
            missedShotLocations.add(moveLocation);
        }

        return true;
    }

    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= boardHeight || y < 0 || y >= boardWidth;
    }

    private boolean isAlreadyTargeted(int x, int y) {
        String moveLocation = String.format("%d,%d", x, y);
        return hitLocations.contains(moveLocation) || missedShotLocations.contains(moveLocation);
    }

    public boolean checkWin(char player) {
        return shipLocations.isEmpty();
    }

    public boolean isGameOver() {
        return shipLocations.isEmpty() || missedShotLocations.size() >= (BOARD_WIDTH * BOARD_HEIGHT);
    }

    public void printBoard() {
        System.out.println("Battleship Board:");
        // Print the board with ships, hits, and misses
        // You can customize the output based on your preference
    }
}
