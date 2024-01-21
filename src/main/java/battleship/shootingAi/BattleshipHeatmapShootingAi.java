package battleship.shootingAi;

import framework.Board;
import framework.Game;

import java.awt.*;
import java.util.ArrayList;

public class BattleshipHeatmapShootingAi implements BattleshipShootingAi {

    Boolean foundShip = false;
    Point lastShot = new Point(-1, -1);
    ArrayList<Point> lastHits = new ArrayList<>();

    Board localBoard = new Board(8, 8);

    ArrayList<Integer> boatsRemaining = new ArrayList<>();

    ArrayList<Point> alreadyShot = new ArrayList<>();
    public BattleshipHeatmapShootingAi() {
        boatsRemaining.add(2);
        boatsRemaining.add(3);
        boatsRemaining.add(4);
        boatsRemaining.add(6);
    }

    @Override
    public Point getMove(Game game) {
        Board board = game.getBoard();
        int[][] heatmap = new int[8][8];

        if (board.get(lastShot) == 'h') {
            localBoard.set(lastShot, 'h');
            lastHits.add(lastShot);
            foundShip = true;
        } else if (Character.isDigit(board.get(lastShot))) {
            lastHits.add(lastShot);
            for (int i = 0; i < lastHits.size(); i++) {

                Point p = lastHits.get(i);
                localBoard.set(p, 'x');
                localBoard.set(p.x - 1, p.y, 'x');
                localBoard.set(p.x + 1, p.y, 'x');
                localBoard.set(p.x, p.y + 1, 'x');
                localBoard.set(p.x, p.y - 1, 'x');
            }
            boatsRemaining.remove((Integer) lastHits.size());
            lastHits.clear();
            foundShip = false;
        }

        generateHeatMap(heatmap);

        int max = 0;
        ArrayList<Point> maxPoints = new ArrayList<>();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++){
                Point location = new Point(x, y);
                if (alreadyShot.contains(location)) {
                    continue;
                }
                if (heatmap[y][x] > max) {
                    max = heatmap[y][x];
                    maxPoints.clear();
                    maxPoints.add(location);
                } else if (heatmap[y][x] == max) {
                    maxPoints.add(location);
                }
            }
        }

//        for (int[] row : heatmap) {
//            for (int i : row) {
//                if (i < 10) {
//                    System.out.print(" ");
//                }
//                System.out.print(i + " ");
//            }
//            System.out.println();
//        }

        Point move = maxPoints.get((int) (Math.random() * maxPoints.size()));
        if (alreadyShot.contains(move)) {
            throw new IllegalStateException("Already shot at " + move);
        }
        localBoard.set(move, 'x');
        lastShot = move;
        alreadyShot.add(move);
        return move;
    }

    public void generateHeatMap(int[][] heatmap) {
        if (foundShip) {
            for (int boat : boatsRemaining) {
                if (lastHits.size() >= boat) {
                    continue;
                }
                if (lastHits.size() > 1) {
                    if (lastHits.get(0).x == lastHits.get(1).x) {
                        //vertical
                        int x = lastHits.get(0).x;
                        for (int y = 0; y < 9-boat; y++) {
                            placeBoat(new Point(x, y), 1, boat, heatmap);
                        }
                    } else {
                        //horizontal
                        int y = lastHits.get(0).y;
                        for (int x = 0; x < 9-boat; x++) {
                            placeBoat(new Point(x, y), 0, boat, heatmap);
                        }
                    }
                } else {
                    //vertical
                    int x = lastHits.get(0).x;
                    for (int y = 0; y < 9-boat; y++) {
                        placeBoat(new Point(x, y), 1, boat, heatmap);
                    }
                    //horizontal
                    int y = lastHits.get(0).y;
                    for (int i = 0; i < 9-boat; i++) {
                        placeBoat(new Point(i, y), 0, boat, heatmap);
                    }
                }
            }
        } else {
            for (int boat : boatsRemaining) {
                // vertical
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8-boat+1; y++) {
                        placeBoat(new Point(x, y), 1, boat, heatmap);
                    }
                }

                //horizontal
                for (int x = 0; x < 8-boat+1; x++) {
                    for (int y = 0; y < 8; y++) {
                        placeBoat(new Point(x, y), 0, boat, heatmap);
                    }
                }
            }
        }
    }

    public void placeBoat(Point location, int orientation, int length, int[][] heatmap) {
        boolean isValid = true;
        int matchingShots = lastHits.size();
        if (orientation == 0) {
            for (int x = location.x; x < location.x + length; x++) {
                if (localBoard.get(x, location.y) == 'x') {
                    isValid = false;
                    break;
                } else if (localBoard.get(x, location.y) == 'h') {
                    matchingShots--;
                }
            }
        } else {
            for (int y = location.y; y < location.y + length; y++) {
                if (localBoard.get(location.x, y) == 'x') {
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
                    heatmap[y][location.x]++;  // Fix this line
                }
            }
        }
    }

}
