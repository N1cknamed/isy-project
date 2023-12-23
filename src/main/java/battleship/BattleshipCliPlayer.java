package battleship;

import Framework.Game;
import Framework.Player;
import Games.Board;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BattleshipCliPlayer implements BattleshipPlayer {

    private final char symbol;
    private final Scanner scanner;
    private Board board;

    private HashMap<Character, Integer> ships = new HashMap<Character, Integer>();

    public BattleshipCliPlayer(char symbol) {
        this.symbol = symbol;
        this.scanner = new Scanner(System.in);
        this.board = new Board(8, 8);
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
                if (board.get(i,move.y) != 0) {
                    return false;
                }
            }
        } else {
            if (move.y + size > 8) {
                return false;
            }
            for (int i = move.y; i < move.y + size; i++) {
                if (board.get(move.x, i) != 0) {
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
            board.printBoard();
            Character boatType = entry.getKey();
            Integer size = entry.getValue();
            int x, y, direction;
            boolean valid = true;
            do {
                if (valid) {
                    System.out.println("Place boat of size " + size);
                } else {
                    System.out.println("Invalid move, place boat of size " + size + " again");
                }
                System.out.println("Enter x coordinate");
                x = scanner.nextInt();
                System.out.println("Enter y coordinate");
                y = scanner.nextInt();
                System.out.println("Enter direction (0 = horizontal, 1 = vertical)");
                direction = scanner.nextInt();
                valid = isValidMove(new Point(x, y), size, direction);
            } while (!valid);

            if (direction == 0) {
                System.out.println("Direction = 0");
                for (int i = x-1; i < x + size + 1; i++) {
                    for (int j = y-1; j <= y+1; j++) {
                        if (i >= 0 && i < board.getBoardWidth() && j >= 0 && j < board.getBoardHeight()) {
                            if (i >= x && i < x + size && y == j) {
                                board.set(i, j, boatType);
                            } else {
                                board.set(i, j, ' ');
                            }
                        }
                    }
                }
            } else {
                for (int i = x-1; i <= x + 1; i++) {
                    for (int j = y-1; j < y+size+1; j++) {
                        if (i >= 0 && i < board.getBoardWidth() && j >= 0 && j < board.getBoardHeight()) {
                            if (j >= y && j < y + size && x == i) {
                                board.set(i, j, boatType);
                            } else {
                                board.set(i, j, ' ');
                            }
                        }
                    }
                }
            }
            System.out.println(board.get(x, y));
        }
    }
}
