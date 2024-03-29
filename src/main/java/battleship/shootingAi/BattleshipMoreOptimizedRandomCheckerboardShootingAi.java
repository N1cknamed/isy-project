package battleship.shootingAi;

import framework.Board;
import framework.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class BattleshipMoreOptimizedRandomCheckerboardShootingAi implements BattleshipShootingAi {

    Boolean foundShip = false;
    Point lastShot = new Point(-1,-1);
    ArrayList<Point> lastHits = new ArrayList<>();
    Board localBoard = new Board(8,8);
    ArrayList<Integer> boatsRemaining = new ArrayList<>();

    public BattleshipMoreOptimizedRandomCheckerboardShootingAi() {
        boatsRemaining.add(2);
        boatsRemaining.add(3);
        boatsRemaining.add(4);
        boatsRemaining.add(6);
    }

    @Override
    public Point getMove(Game game) {
        Board board = game.getBoard();
        Point move;

        if (board.get(lastShot) == 'h') {
            localBoard.set(lastShot, 'x');
            lastHits.add(lastShot);
            foundShip = true;
        } else if (Character.isDigit(board.get(lastShot))) {
            lastHits.add(lastShot);
            for (int i = 0; i < lastHits.size(); i++) {
                Point p = lastHits.get(i);
                localBoard.set(p, 'x');
                localBoard.set(p.x-1, p.y, 'x');
                localBoard.set(p.x+1, p.y, 'x');
                localBoard.set(p.x, p.y+1, 'x');
                localBoard.set(p.x, p.y-1, 'x');
            }
            lastHits.clear();
            foundShip = false;
            boatsRemaining.remove((Integer) (board.get(lastShot) - '0'));
        }

        if (foundShip) {
            if (lastHits.size() == 1){
                ArrayList<Point> possibleMoves = new ArrayList<>();
                move = new Point(lastHits.get(0).x + 1, lastHits.get(0).y);
                if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                    possibleMoves.add(move);
                }
                move = new Point(lastHits.get(0).x - 1, lastHits.get(0).y);
                if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                    possibleMoves.add(move);
                }
                move = new Point(lastHits.get(0).x, lastHits.get(0).y + 1);
                if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                    possibleMoves.add(move);
                }
                move = new Point(lastHits.get(0).x, lastHits.get(0).y - 1);
                if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                    possibleMoves.add(move);
                }
                Random random = new Random();
                move = possibleMoves.get(random.nextInt(possibleMoves.size()));
                lastShot = move;
                localBoard.set(move, 's');
                return move;
            } else {
                int direction = lastHits.get(0).x == lastHits.get(1).x ? 1 : 0;
                for (Point lastHit : lastHits) {
                    if (direction == 0) {
                        ArrayList<Point> possibleMoves = new ArrayList<>();
                        move = new Point(lastHit.x + 1, lastHit.y);
                        if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                            possibleMoves.add(move);
                        }
                        move = new Point(lastHit.x - 1, lastHit.y);
                        if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                            possibleMoves.add(move);
                        }
                        if (!possibleMoves.isEmpty()) {
                            Random random = new Random();
                            move = possibleMoves.get(random.nextInt(possibleMoves.size()));
                            lastShot = move;
                            localBoard.set(move, 's');
                            return move;
                        }
                    } else {
                        ArrayList<Point> possibleMoves = new ArrayList<>();
                        move = new Point(lastHit.x, lastHit.y + 1);
                        if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                            possibleMoves.add(move);
                        }
                        move = new Point(lastHit.x, lastHit.y - 1);
                        if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                            possibleMoves.add(move);
                        }
                        if (!possibleMoves.isEmpty()) {
                            Random random = new Random();
                            move = possibleMoves.get(random.nextInt(possibleMoves.size()));
                            lastShot = move;
                            localBoard.set(move, 's');
                            return move;
                        }
                    }
                }
            }
        }



        Random random = new Random();

        int minBoatSize = boatsRemaining.stream()
                .mapToInt(i -> i)
                .min()
                .orElseThrow();

        do {
            // random move
            move = new Point(random.nextInt(board.getBoardWidth()), random.nextInt(board.getBoardHeight()));
        } while (!(game.isValidMove(move) && localBoard.get(move) == 0 && (move.x + move.y) % minBoatSize == 0));

        lastShot = move;
        localBoard.set(move, 's');
        return move;
    }
}