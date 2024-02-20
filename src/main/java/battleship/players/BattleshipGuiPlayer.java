package battleship.players;

import java.awt.Point;
import java.util.*;

import battleship.Boat;
import framework.Board;
import framework.Game;
import framework.PlayerType;
import ttt.players.TttGuiPlayer;

public class BattleshipGuiPlayer implements BattleshipPlayer {

    private final char symbol;
    private final Board board;
    private final HashMap<Character, Integer> ships = new HashMap<Character, Integer>();

    private Set<Point> alreadyHit = new HashSet<>();
    private int boatsRemaining = 0;
    private final Collection<Boat> boats = new ArrayList<>();

    private static int row = -1;
    private static int col = -1;

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
        // TODO
        // Get shooting position
        return new Point(row, col);
    }

    @Override
    public void placeBoats() {
        // TODO
        // Logic for placing boats
//        ships.put('2', 2);
//        ships.put('3', 3);
//        ships.put('4', 4);
//        ships.put('6', 6);
//        for (Map.Entry<Character, Integer> entry : ships.entrySet()) {
//            //board.printBoard();
//            Character boatType = entry.getKey();
//            Integer size = entry.getValue();
//            int x, y, direction;
//            boolean valid = true;
//            do {
//                if (valid) {
//                    //System.out.println("Place boat of size " + size);
//                } else {
//                    //System.out.println("Invalid move, place boat of size " + size + " again");
//                }
//                //System.out.println("Enter x coordinate");
//                //x = scanner.nextInt();
//                //System.out.println("Enter y coordinate");
//                //y = scanner.nextInt();
//                //System.out.println("Enter direction (0 = horizontal, 1 = vertical)");
//                //direction = scanner.nextInt();
//                //valid = isValidMove(new Point(x, y), size, direction);
//            } while (!valid);
//
//            Boat boat = new Boat(new Point(x, y), direction, size);
//            boats.add(boat);
//
//            // TODO remove code repeat
//            if (direction == 0) {
//                // place boat and surround with spaces
//                for (int i = x; i < x + size; i++) {
//                    board.set(i, y, boatType);
//                    //if suroungding 4 spaces are 0 place a ' '
//                    if (board.get(i-1, y) == 0) {
//                        board.set(i-1, y, ' ');
//                    }
//                    if (board.get(i+1, y) == 0) {
//                        board.set(i+1, y, ' ');
//                    }
//                    if (board.get(i, y+1) == 0) {
//                        board.set(i, y+1, ' ');
//                    }
//                    if (board.get(i, y-1) == 0) {
//                        board.set(i, y-1, ' ');
//                    }
//
//                }
//            } else {
//                for (int i = y; i < y + size; i++) {
//                    board.set(x, i, boatType);
//                    //if suroungding 4 spaces are 0 place a ' '
//                    if (board.get(x-1, i) == 0) {
//                        board.set(x-1, i, ' ');
//                    }
//                    if (board.get(x+1, i) == 0) {
//                        board.set(x+1, i, ' ');
//                    }
//                    if (board.get(x, i+1) == 0) {
//                        board.set(x, i+1, ' ');
//                    }
//                    if (board.get(x, i-1) == 0) {
//                        board.set(x, i-1, ' ');
//                    }
//
//                }
//            }
//            boatsRemaining++;
//            //System.out.println(board.get(x, y));
//        }
//        //board.printBoard();
//        //System.out.printf("Player %s has placed all their boats\n", symbol);
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
        //System.out.println("your shot was a " + rt);
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

    public static void setMove(int row, int col) {
        BattleshipGuiPlayer.row = row;
        BattleshipGuiPlayer.col = col;
    }
    
}
