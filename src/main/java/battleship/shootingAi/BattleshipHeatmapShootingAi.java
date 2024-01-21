package battleship.shootingAi;

import framework.Board;
import framework.Game;

import java.awt.*;
import java.util.ArrayList;

public class BattleshipHeatmapShootingAi implements BattleshipShootingAi {
    
    Boolean foundShip = false;
    Point lastShot = new Point(-1,-1);
    ArrayList<Point> lastHits = new ArrayList<>();

    Board localBoard = new Board(8,8);

    ArrayList<Integer> boatsRemaining = new ArrayList<>();
    
    public BattleshipHeatmapShootingAi() {
        boatsRemaining.add(2);
        boatsRemaining.add(3);
        boatsRemaining.add(4);
        boatsRemaining.add(6);
    }

    @Override
    public Point getMove(Game game) {
        Board board = game.getBoard();
        Point move;
        int[][] heatmap = new int[8][8];

        if (board.get(lastShot) == 'h') {
            localBoard.set(lastShot, 'h');
            lastHits.add(lastShot);
            foundShip = true;
        } else if (Character.isDigit(board.get(lastShot))) {
            for (int i = 0; i < lastHits.size(); i++) {
                Point p = lastHits.get(i);
                localBoard.set(p, 'x');
                localBoard.set(p.x-1, p.y, 'x');
                localBoard.set(p.x+1, p.y, 'x');
                localBoard.set(p.x, p.y+1, 'x');
                localBoard.set(p.x, p.y-1, 'x');
            }
            boatsRemaining.remove(boatsRemaining.indexOf(lastHits.size()));
            lastHits.clear();
            foundShip = false;
        }

        if (foundShip) {
            for (int boat : boatsRemaining) {
                if (lastHits.size() >= boat) {
                    continue;
                }
                if (lastHits.size() > 1 ) {
                    if (lastHits.get(0).x == lastHits.get(1).x) {
                        //vertical
                    } else {
                        //horizontal
                    }
                } else {
                    
                }
            }
        }
        return new Point();
    }

    public void placeBoat(Point location, int orientation, int length, int[][] heatmap) {
        boolean isValid = true;
        int matchingShots = lastHits.size();
        if (orientation == 0) {
            for (int x = location.x; x < location.x + length; x++) {
                if (localBoard.get(x, location.y) == 'x')  {
                    isValid = false;
                    break;
                } else if (localBoard.get(x, location.y) == 'h') {
                    matchingShots--;
                }
            }
        } else {
            for (int y = location.y; y < location.y + length; y++) {
                if (localBoard.get(location.x, y) == 'x')  {
                    isValid = false;
                    break;
                } else if (localBoard.get(location.x, y) == 'h') {
                    matchingShots--;
                }
            }
        }
        if (matchingShots != 0) {
            isValid = false;
        }
        if (isValid) {
            if (orientation == 0) {
                for (int x = location.x; x < location.x + length; x++) {
                    heatmap[location.y][x]++;
                }
            } else {
                for (int y = location.y; y < location.y + length; y++) {
                    heatmap[y][location.y]++;
                }
            }
        }
    }

}
