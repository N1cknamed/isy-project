package battleship.players;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
        return new Point();
    }

    @Override
    public void placeBoats() {
        // TODO
        // Logic for placing boats
    }

    @Override
    public char shoot(Point move) {
        // TODO
        // Shooting logic
        char rt;
        rt = 'h'; //temp value
        return rt;
    }

    @Override
    public boolean isAlive() {
        return boatsRemaining < 0;
    }

    @Override
    public Collection<Boat> getPlacedBoats() {
        return boats;
    }
    
}
