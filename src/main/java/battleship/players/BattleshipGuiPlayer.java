package battleship.players;

import java.awt.Point;
import java.util.*;

import battleship.Boat;
import framework.Board;
import framework.Game;
import framework.PlayerType;

public class BattleshipGuiPlayer implements BattleshipPlayer {

    private final char symbol;
    private final Board board;
    private final HashMap<Character, Integer> ships = new HashMap<Character, Integer>();

    private Set<Point> alreadyHit = new HashSet<>();
    private int boatsRemaining = 0;
    private final Collection<Boat> boats = new ArrayList<>();

    private static int x = -1;
    private static int y = -1;
    private static int direction = 0;

    public BattleshipGuiPlayer(char symbol) {
        this.symbol = symbol;
        this.board = new Board(8, 8);
    }

    @Override
    public PlayerType getPlayerType() {
        return BattleshipPlayerType.GUI;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public Point doMove(Game game) throws InterruptedException {
        x = -1;
        y = -1;
        while ((x == -1 || y == -1) || !game.isValidMove(new Point(x, y))) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new Point(x, y);
    }

    private boolean isValidMove(Point move, int size, int direction) {
        if (move.x < 0 || move.y < 0) {
            return false;
        }
        if (direction == 2) {
            return false;
        }
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

    public int getDirection(int x, int y, int x2, int y2) {
        if (x == x2 && y != y2) {
            return 0;
        } else if (x != x2 && y == y2) {
            return 1;
        } else return 2;
    }

    @Override
    public void placeBoats() {
        // TODO Logic for placing boats
        ships.put('2', 2);
        ships.put('3', 3);
        ships.put('4', 4);
        ships.put('6', 6);
        for (Map.Entry<Character, Integer> entry : ships.entrySet()) {
            Character boatType = entry.getKey();
            Integer size = entry.getValue();
            boolean valid = true;
            do {

                System.out.println(); // needed for code to work?

                valid = isValidMove(new Point (x, y), size, direction);

            } while (!valid);

            // TODO remove code repeat
            if (direction == 0) { 
                // x
                // place boat and surround with spaces
                Boat boat = new Boat(new Point(x, y), direction, size);
                boats.add(boat);
                
                System.out.println(boat.getStartCord());

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
                // y
                Boat boat = new Boat(new Point(x, y), direction, size);
                boats.add(boat);
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
        }
    }

    @Override
    public char shoot(Point move) {
        // TODO
        // Shooting logic
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

    public static void setMove(int x, int y) {
            BattleshipGuiPlayer.x = x;
            BattleshipGuiPlayer.y = y;   
    }

    public static void setDirection(int direction) {
        BattleshipGuiPlayer.direction = direction;
    }
}
