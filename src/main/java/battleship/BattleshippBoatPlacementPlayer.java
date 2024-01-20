package battleship;

import framework.Board;
import framework.Game;
import framework.PlayerType;

import java.awt.*;
import java.util.*;

public class BattleshippBoatPlacementPlayer implements BattleshipPlayer{
    private final char symbol;
    private final Board board;
    private final HashMap<Character, Integer> ships = new HashMap<Character, Integer>();
    private final Set<Point> alreadyHit = new HashSet<>();
    private Collection<Boat> boats = new ArrayList<>();

    // for the ai
    private int boatsRemaining = 0;

    public BattleshippBoatPlacementPlayer(char symbol) {
        this.symbol = 's';
        this.board = new Board(8, 8);
    }

    private boolean isValidBoatPlacement(Point move, int size, int direction) {
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
        ships.put('2', 2);
        //ships.put('3', 3);
        //ships.put('4', 4);
        //ships.put('6', 6);

        for (Map.Entry<Character, Integer> entry : ships.entrySet()) {
            boolean placed = false;

            for (int x = 0; x < board.getBoardWidth(); x++) {
                for (int y = 0; y < board.getBoardHeight(); y++) {
                    int direction = 0;
                    Point cords = new Point(x, y);
                    int size = entry.getValue();
                    if (isValidBoatPlacement(cords, size, direction)) {
                        char boatType = entry.getKey();
                        for (int i = x-1; i < x + size + 1; i++) {
                            for (int j = y-1; j <= y+1; j++) {
                                // check if in bounds
                                if (i >= 0 && i < board.getBoardWidth() && j >= 0 && j < board.getBoardHeight()) {
                                    if (i >= x && i < x + size && y == j) {
                                        board.set(i, j, boatType);
                                    } else {
                                        board.set(i, j, ' ');
                                    }
                                }
                            }
                        }
                        placed = true;
                        Boat boat = new Boat(cords, direction, size);
                        boats.add(boat);
                        break;
                    }
                }
                if (placed) break;
            }

            boatsRemaining++;
        }
//        board.printBoard();
    }

    @Override
    public char shoot(Point move) {
        char result = board.get(move.x, move.y);
        char rt;
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

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.AI;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public Point doMove(Game game) {
        return new Point(7,7);
    }
}
