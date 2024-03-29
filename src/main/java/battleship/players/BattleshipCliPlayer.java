package battleship.players;

import battleship.Boat;
import battleship.players.BattleshipPlayer;
import framework.Game;
import framework.PlayerType;
import framework.Board;

import java.awt.*;
import java.util.*;

public class BattleshipCliPlayer implements BattleshipPlayer {

    private final char symbol;
    private final Scanner scanner;
    private final Board board;
    private final HashMap<Character, Integer> ships = new HashMap<Character, Integer>();

    private Set<Point> alreadyHit = new HashSet<>();
    private int boatsRemaining = 0;
    private final Collection<Boat> boats = new ArrayList<>();

    public BattleshipCliPlayer(char symbol) {
        this.symbol = symbol;
        this.scanner = new Scanner(System.in);
        this.board = new Board(8, 8);
    }

    @Override

    public PlayerType getPlayerType() {
        return BattleshipPlayerType.CLI;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public Point doMove(Game game) {
        int x, y;
        boolean notFirst = false;
        do {
            if (notFirst) System.out.println("Invalid shot choose another location");
            System.out.printf("Player %s's turn\n", symbol);
            System.out.print("Enter x (0-7): ");
            x = scanner.nextInt();
            System.out.print("Enter y (0-7): ");
            y = scanner.nextInt();
            notFirst = true;
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

            Boat boat = new Boat(new Point(x, y), direction, size);
            boats.add(boat);

            // TODO remove code repeat
            if (direction == 0) {
                // place boat and surround with spaces
                for (int i = x; i < x + size; i++) {
                    board.set(i, y, boatType);
                    //if suroungding 4 spaces are 0 place a ' '
                    if (board.get(i-1, y) == 0) {
                        board.set(i-1, y, ' ');
                    }
                    if (board.get(i+1, y) == 0) {
                        board.set(i+1, y, ' ');
                    }
                    if (board.get(i, y+1) == 0) {
                        board.set(i, y+1, ' ');
                    }
                    if (board.get(i, y-1) == 0) {
                        board.set(i, y-1, ' ');
                    }

                }
            } else {
                for (int i = y; i < y + size; i++) {
                    board.set(x, i, boatType);
                    //if suroungding 4 spaces are 0 place a ' '
                    if (board.get(x-1, i) == 0) {
                        board.set(x-1, i, ' ');
                    }
                    if (board.get(x+1, i) == 0) {
                        board.set(x+1, i, ' ');
                    }
                    if (board.get(x, i+1) == 0) {
                        board.set(x, i+1, ' ');
                    }
                    if (board.get(x, i-1) == 0) {
                        board.set(x, i-1, ' ');
                    }

                }
            }
            boatsRemaining++;
            System.out.println(board.get(x, y));
        }
        board.printBoard();
        System.out.printf("Player %s has placed all their boats\n", symbol);
    }

    @Override
    public char shoot(Point move) {
        char result = board.get(move.x, move.y);
        char rt;
        // TODO: remove m
        if (result == ' ' || result == 0 || result == 'm') {
            rt = 'm';
        } else {
            rt = 'h';
            // TODO: maybe not hit but mis
            if (alreadyHit.contains(move)) return 'h';
            board.set(move.x, move.y, 'x');
            int shotsRemaining = ships.get(result);
            shotsRemaining--;
            ships.put(result, shotsRemaining);
            if (shotsRemaining <= 0) {
                boatsRemaining--;
                rt = result;
            }
        }
        alreadyHit.add(move);
        System.out.println("your shot was a " + rt);
        return rt;
    }

    @Override
    public boolean isAlive() {
        return boatsRemaining > 0;
    }

    @Override
    public Collection<Boat> getPlacedBoats() {
        return boats;
    }
}
