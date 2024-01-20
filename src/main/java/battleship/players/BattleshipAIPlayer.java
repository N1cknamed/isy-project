package battleship.players;

import battleship.Boat;
import battleship.placementsStrategy.BattleshipPlacementStrategy;
import battleship.shootingAi.BattleshipShootingAi;
import framework.Board;
import framework.Game;
import framework.PlayerType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class BattleshipAIPlayer implements BattleshipPlayer {

    private final char symbol;
    private final BattleshipPlayerType playerType;
    private final BattleshipPlacementStrategy placementStrategy;
    private final BattleshipShootingAi shootingAi;

    private final Board board;
    private final Collection<Boat> placedBoats = new ArrayList<>();
    private final HashMap<Character, Integer> ships = new HashMap<Character, Integer>();
    private int boatsRemaining = 0;

    public BattleshipAIPlayer(char symbol, BattleshipPlayerType playerType) {
        this.symbol = symbol;
        this.playerType = playerType;
        this.placementStrategy = playerType.createPlacementStrategy();
        this.shootingAi = playerType.createShootingAi();

        this.board = new Board(8, 8);
    }


    private boolean isValidBoatPlacement(Point move, int size, int direction) {
        if (direction == 0) {
            if (move.x + size > 8 || move.y > 8 || move.x < 0 || move.y < 0) {
                return false;
            }
            for (int i = move.x; i < move.x + size; i++) {
                if (board.get(i, move.y) != 0) {
                    return false;
                }
            }
        } else {
            if (move.y + size > 8 || move.y > 8 || move.x < 0 || move.y < 0) {
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
        ArrayList<Boat> shipsLocations = placementStrategy.getShips();
        placedBoats.addAll(shipsLocations);

        for (Boat ship : shipsLocations) {
            Point location = ship.getStartCord();
            int direction = ship.getDirection();
            int size = ship.getLength();
            char boatType = (char) (ship.getLength() + '0');

            if (!isValidBoatPlacement(location, size, direction)) {
                throw new IllegalStateException("Invalid Boatlocation");
            }

            ships.put(boatType, ship.getLength());

            if (direction == 0) {
                // place boat and surround with spaces
                for (int i = location.x; i < location.x + size; i++) {
                    board.set(i, location.y, boatType);
                    //if suroungding 4 spaces are 0 place a ' '
                    if (board.get(i - 1, location.y) == 0) {
                        board.set(i - 1, location.y, ' ');
                    }
                    if (board.get(i + 1, location.y) == 0) {
                        board.set(i + 1, location.y, ' ');
                    }
                    if (board.get(i, location.y + 1) == 0) {
                        board.set(i, location.y + 1, ' ');
                    }
                    if (board.get(i, location.y - 1) == 0) {
                        board.set(i, location.y - 1, ' ');
                    }

                }
            } else if (direction == 1) {
                for (int i = location.y; i < location.y + size; i++) {
                    board.set(location.x, i, boatType);
                    //if suroungding 4 spaces are 0 place a ' '
                    if (board.get(location.x - 1, i) == 0) {
                        board.set(location.x - 1, i, ' ');
                    }
                    if (board.get(location.x + 1, i) == 0) {
                        board.set(location.x + 1, i, ' ');
                    }
                    if (board.get(location.x, i + 1) == 0) {
                        board.set(location.x, i + 1, ' ');
                    }
                    if (board.get(location.x, i - 1) == 0) {
                        board.set(location.x, i - 1, ' ');
                    }

                }
            } else {
                throw new RuntimeException("Invalid boat rotation: " + direction);
            }
            boatsRemaining++;
        }
//        board.printBoard();
    }

    @Override
    public Point doMove(Game game) throws InterruptedException {
        return shootingAi.getMove(game);
    }

    @Override
    public char shoot(Point move) {
        char result = board.get(move.x, move.y);

        if (result == ' ' || result == 0 || result == 'm') {
            return 'm';
        } else if (result == 'x') {
            throw new IllegalStateException("Shot twice");
        } else {
            char rt = 'h';
            board.set(move.x, move.y, 'x');
            int shotsRemaining = ships.get(result);
            shotsRemaining--;
            ships.put(result, shotsRemaining);
            if (shotsRemaining <= 0) {
                boatsRemaining--;
                rt = result;
            }

            return rt;
        }
    }

    @Override
    public boolean isAlive() {
        return boatsRemaining > 0;
    }

    @Override
    public Collection<Boat> getPlacedBoats() {
        return placedBoats;
    }

    @Override
    public PlayerType getPlayerType() {
        return playerType;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
