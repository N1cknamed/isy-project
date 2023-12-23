package battleship;

import Framework.Game;
import Framework.Player;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BattleshipCliPlayer implements BattleshipPlayer {

    private final char symbol;
    private final Scanner scanner;
    private char[][] board;

    private HashMap<Character, Integer> ships = new HashMap<Character, Integer>();

    public BattleshipCliPlayer(char symbol) {
        this.symbol = symbol;
        this.scanner = new Scanner(System.in);
        this.board = new char[8][8];
    }


    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public char getSymbol() {
        return 0;
    }

    @Override
    public Point doMove(Game game) {
        int x, y;
        do {
            System.out.printf("Player %s's turn\n", symbol);
            System.out.print("Enter row (0-2): ");
            y = scanner.nextInt();
            System.out.print("Enter column (0-2): ");
            x = scanner.nextInt();
        } while (!game.isValidMove(new Point(x, y)));

        return new Point(x, y);
    }

    private boolean isValidMove(Point move, int size, int direction) {
        if (direction == 0) {
            if (move.x + size > 8) {
                return false;
            }
            for (int i = move.x; i < move.x + size; i++) {
                if (board[i][move.y] != 0) {
                    return false;
                }
            }
        } else {
            if (move.y + size > 8) {
                return false;
            }
            for (int i = move.y; i < move.y + size; i++) {
                if (board[move.x][i] != 0) {
                    return false;
                }
            }
        }
        return true;

    }
    @Override
    public void placeBoats() {
        System.out.println("Player " + symbol + " place your boats");
        ships.put('2', 2);
        ships.put('3', 3);
        ships.put('4', 4);
        ships.put('6', 6);

        for (Map.Entry<Character, Integer> entry : ships.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();
            int x, y, direction;
            boolean valid = true;
            do {
                if (valid) {
                    System.out.println("Place boat of size " + value);
                } else {
                    System.out.println("Invalid move, place boat of size " + value + " again");
                }
                System.out.println("Enter x coordinate");
                x = scanner.nextInt();
                System.out.println("Enter y coordinate");
                y = scanner.nextInt();
                System.out.println("Enter direction (0 = horizontal, 1 = vertical)");
                direction = scanner.nextInt();
                valid = isValidMove(new Point(x, y), value, direction);
            } while (!valid);
        }
    }
}
